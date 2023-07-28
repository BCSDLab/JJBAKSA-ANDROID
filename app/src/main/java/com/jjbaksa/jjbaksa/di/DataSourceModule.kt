package com.jjbaksa.jjbaksa.di

import android.content.Context
import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.database.UserDao
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.SearchRemoteDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(authApi: AuthApi, noAuthApi: NoAuthApi): UserRemoteDataSource {
        return UserRemoteDataSource(authApi, noAuthApi)
    }
    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context, userDao: UserDao): UserLocalDataSource {
        return UserLocalDataSource(context, userDao)
    }
    @Provides
    @Singleton
    fun provideRemoteSearchDataSource(authApi: AuthApi, noAuthApi: NoAuthApi): SearchRemoteDataSource {
        return SearchRemoteDataSource(authApi, noAuthApi)
    }
}
