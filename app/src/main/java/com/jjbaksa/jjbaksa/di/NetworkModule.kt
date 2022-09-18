package com.jjbaksa.jjbaksa.di

import com.jjbaksa.data.BASE_URL
import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.jjbaksa.JjbaksaApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            // val accessToken //todo 추후 ACCESS TOKEN 추가
            val newRequest: Request = chain.request().newBuilder()
                // .addHeader("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(newRequest)
        }
    }
    // TODO 추후 Refresh 추가
    /*
    @AUTH
    @Provides
    @Singleton
    fun provideTokenAuthenticator(@REFRESH refreshRetrofit: Retrofit): TokenAuthenticator {
        return TokenAuthenticator(
            KoalaApp.instance.applicationContext,
            refreshRetrofit
        )
    }

     */
    // TODO 추후 Refresh 추가
    /*
    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val refreshToken = Hawk.get(REFRESH_TOKEN, "")
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("RefreshToken", "Bearer $refreshToken")
                .build()
            chain.proceed(newRequest)
        }
    }

     */

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
    /*
    @AUTH
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        @AUTH authInterceptor: Interceptor,
        @AUTH tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
            authenticator(tokenAuthenticator)
        }.build()
    }

     */

    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshOkHttpClient(
        @REFRESH refreshAuthInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(refreshAuthInterceptor)
        }.build()
    }

    @NOAUTH
    @Provides
    @Singleton
    fun provideNoAuthRetrofit(@NOAUTH okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
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

    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshRetrofit(@REFRESH okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @NOAUTH
    @Provides
    @Singleton
    fun provideNoAuthApi(@NOAUTH retrofit: Retrofit): NoAuthApi {
        return retrofit.create(NoAuthApi::class.java)
    }

    @AUTH
    @Provides
    @Singleton
    fun provideAuthApi(@AUTH retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshApi(@REFRESH retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}
