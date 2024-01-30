package com.jjbaksa.jjbaksa.di

import android.content.Context
import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.api.TestNoAuthApi
import com.jjbaksa.data.database.UserDao
import com.jjbaksa.data.datasource.local.HomeLocalDataSource
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.FollowerRemoteDataSource
import com.jjbaksa.data.datasource.remote.HomeRemoteDataSource
import com.jjbaksa.data.datasource.remote.InquiryRemoteDataSource
import com.jjbaksa.data.datasource.remote.ShopRemoteDataSource
import com.jjbaksa.data.datasource.remote.PostRemoteDataSource
import com.jjbaksa.data.datasource.remote.ReviewRemoteDataSource
import com.jjbaksa.data.datasource.remote.ScrapRemoteDataSource
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
    fun provideUserLocalDataSource(
        @ApplicationContext context: Context,
        userDao: UserDao
    ): UserLocalDataSource {
        return UserLocalDataSource(context, userDao)
    }

    @Provides
    @Singleton
    fun provideHomeRemoteDataSource(authApi: AuthApi, noAuthApi: NoAuthApi): HomeRemoteDataSource {
        return HomeRemoteDataSource(authApi, noAuthApi)
    }

    @Provides
    @Singleton
    fun provideHomeLocalDataSource(
        @ApplicationContext context: Context,
        userDao: UserDao
    ): HomeLocalDataSource {
        return HomeLocalDataSource(context, userDao)
    }

    @Provides
    @Singleton
    fun provideRemoteSearchDataSource(
        authApi: AuthApi,
        noAuthApi: NoAuthApi,
        testNoAuthApi: TestNoAuthApi
    ): SearchRemoteDataSource {
        return SearchRemoteDataSource(authApi, noAuthApi)
    }

    @Provides
    @Singleton
    fun provideRemoteInquiryDataSource(
        authApi: AuthApi,
        noAuthApi: NoAuthApi
    ): InquiryRemoteDataSource {
        return InquiryRemoteDataSource(authApi, noAuthApi)
    }

    @Provides
    @Singleton
    fun provideRemotePostDataSource(authApi: AuthApi, noAuthApi: NoAuthApi): PostRemoteDataSource {
        return PostRemoteDataSource(authApi, noAuthApi)
    }

    @Provides
    @Singleton
    fun provideRemoteShopDataSource(
        authApi: AuthApi,
        noAuthApi: NoAuthApi,
        testNoAuthApi: TestNoAuthApi
    ): ShopRemoteDataSource {
        return ShopRemoteDataSource(authApi, noAuthApi, testNoAuthApi)
    }

    @Provides
    @Singleton
    fun provideRemoteScrapDataSource(
        authApi: AuthApi,
        noAuthApi: NoAuthApi,
        testNoAuthApi: TestNoAuthApi
    ): ScrapRemoteDataSource {
        return ScrapRemoteDataSource(authApi, noAuthApi, testNoAuthApi)
    }

    @Provides
    @Singleton
    fun provideRemoteReviewDataSource(
        authApi: AuthApi,
        noAuthApi: NoAuthApi
    ): ReviewRemoteDataSource {
        return ReviewRemoteDataSource(authApi, noAuthApi)
    }

    @Provides
    @Singleton
    fun provideFollowerRemoteDataSource(authApi: AuthApi, noAuthApi: NoAuthApi): FollowerRemoteDataSource {
        return FollowerRemoteDataSource(authApi, noAuthApi)
    }
}
