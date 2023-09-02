package com.jjbaksa.jjbaksa.di

import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jjbaksa.data.BASE_URL
import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.api.RefreshApi
import com.jjbaksa.data.api.TestNoAuthApi
import com.jjbaksa.data.database.PreferenceKeys
import com.jjbaksa.data.database.userDataStore
import com.jjbaksa.jjbaksa.JjbaksaApp
import com.jjbaksa.jjbaksa.ui.login.LoginActivity
import com.jjbaksa.jjbaksa.util.RefreshManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AUTH

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NOAUTH

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TEST

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class REFRESH

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (JjbaksaApp.instance.isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.HEADERS
        }
    }

    @AUTH
    @Provides
    @Singleton
    fun provideAuthInterceptor(@ApplicationContext context: Context): Interceptor {

        return Interceptor { chain: Interceptor.Chain ->
            runBlocking {
                val key = stringPreferencesKey("ACEESS_TOKEN")
                val accessToken: String = context.userDataStore.data.first()[key] ?: ""
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
                chain.proceed(newRequest)
            }
        }
    }
    @REFRESH
    @Provides
    @Singleton
    fun providerRefreshInterceptor(
        @ApplicationContext appContext: Context,
    ): Interceptor {
        val retryInterceptor: Interceptor = Interceptor { chain ->
            runBlocking {
                var request = chain.request().newBuilder().build()
                var response = chain.proceed(request)
                val dataStore = appContext.userDataStore

                if (response.code == 401) {

                    val refreshApi = RefreshManager.getClient(appContext)!!.create(RefreshApi::class.java)
                    var result = refreshApi.getRefreshAuth()
                    if (result.isSuccessful) {
                        dataStore.edit {
                            it[PreferenceKeys.ACCESS_TOKEN] = result.body()!!.accessToken ?: ""
                        }
                        dataStore.edit {
                            it[PreferenceKeys.REFRESH_TOKEN] = result.body()!!.refreshToken ?: ""
                        }
                        request = response.request
                            .newBuilder()
                            .removeHeader("Authorization")
                            .addHeader(
                                "Authorization",
                                "Bearer ${result.body()!!.accessToken}"
                            )
                            .build()
                        response = chain.proceed(request)
                    } else {
                        dataStore.edit {
                            it[PreferenceKeys.ACCESS_TOKEN] = ""
                        }
                        dataStore.edit {
                            it[PreferenceKeys.REFRESH_TOKEN] = ""
                        }
                        val intent = Intent(appContext, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        appContext.startActivity(intent)
                    }
                }
                response
            }
        }
        return retryInterceptor
    }
    @NOAUTH
    @Provides
    @Singleton
    fun provideNoAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    // TODO 추후 Refresh 추가
    @AUTH
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        @AUTH authInterceptor: Interceptor,
        @REFRESH refreshInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
            addInterceptor(refreshInterceptor)
        }.build()
    }

    @NOAUTH
    @Provides
    @Singleton
    fun provideNoAuthRetrofit(@NOAUTH okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AUTH
    @Provides
    @Singleton
    fun provideAuthRetrofit(@AUTH okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNoAuthApi(@NOAUTH retrofit: Retrofit): NoAuthApi {
        return retrofit.create(NoAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(@AUTH retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    /**
     * MOCK TEST
     */
    @TEST
    @Provides
    @Singleton
    fun testProvideNoAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }
    @TEST
    @Provides
    @Singleton
    fun testProvideNoAuthRetrofit(@TEST okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun testProvideNoAuthApi(@TEST retrofit: Retrofit): TestNoAuthApi {
        return retrofit.create(TestNoAuthApi::class.java)
    }
}
