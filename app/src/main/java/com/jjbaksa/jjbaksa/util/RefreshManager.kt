package com.jjbaksa.jjbaksa.util

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jjbaksa.data.BASE_URL
import com.jjbaksa.data.database.userDataStore
import com.jjbaksa.jjbaksa.BuildConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RefreshManager {
    private var retrofitClient: Retrofit? = null
    fun getClient(appContext: Context): Retrofit? {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.HEADERS
            }
        }
        val refreshHeaderInterceptor = Interceptor { chain: Interceptor.Chain ->
            runBlocking {
                val key = stringPreferencesKey("REFRESH_TOKEN")
                val refreshToken: String = appContext.userDataStore.data.first()[key] ?: ""
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $refreshToken")
                    .build()
                chain.proceed(newRequest)
            }
        }
        var okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(refreshHeaderInterceptor)
        }.build()
        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}
