package com.jjbaksa.data.api

import com.google.gson.GsonBuilder
import com.jjbaksa.data.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gsonBuilder = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    @Singleton
    @Provides
    fun provideNoAuthApiService(retrofit: Retrofit): NoAuthApi {
        return retrofit.create(NoAuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}
