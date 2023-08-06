package com.jjbaksa.jjbaksa.di

import android.content.ContentResolver
import android.content.Context
import com.jjbaksa.data.datasource.local.HomeLocalDataSource
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.HomeRemoteDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.example.imageselector.repository.ImageRepositoryImpl
import com.jjbaksa.data.repository.HomeRepositoryImpl
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.data.repository.UserRepositoryImpl
import com.example.imageselector.repository.ImageRepository
import com.jjbaksa.data.datasource.remote.InquiryRemoteDataSource
import com.jjbaksa.data.datasource.remote.MapRemoteDataSource
import com.jjbaksa.data.datasource.remote.SearchRemoteDataSource
import com.jjbaksa.data.repository.InquiryRepositoryImpl
import com.jjbaksa.data.repository.SearchRepositoryImpl
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.repository.InquiryRepository
import com.jjbaksa.data.datasource.remote.PostRemoteDataSource
import com.jjbaksa.data.repository.MapRepositoryImpl
import com.jjbaksa.data.repository.PostRepositoryImpl
import com.jjbaksa.domain.repository.MapRepository
import com.jjbaksa.domain.repository.PostRepository
import com.jjbaksa.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
    }
    @Singleton
    @Provides
    fun provideImageRepository(
        contentResolver: ContentResolver
    ): ImageRepository {
        return ImageRepositoryImpl(contentResolver)
    }
    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }
    @Singleton
    @Provides
    fun provideHomeRepository(
        homeRemoteDataSource: HomeRemoteDataSource,
        homeLocalDataSource: HomeLocalDataSource
    ): HomeRepository {
        return HomeRepositoryImpl(homeRemoteDataSource, homeLocalDataSource)
    }
    @Singleton
    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: SearchRemoteDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(searchRemoteDataSource)
    }
    @Singleton
    @Provides
    fun provideInquiryRepository(
        inquiryRemoteDataSource: InquiryRemoteDataSource
    ): InquiryRepository {
        return InquiryRepositoryImpl(inquiryRemoteDataSource)
    }
    @Singleton
    @Provides
    fun providePostRepository(
        postRemoteDataSource: PostRemoteDataSource
    ): PostRepository {
        return PostRepositoryImpl(postRemoteDataSource)
    }
    @Singleton
    @Provides
    fun provideMapRepository(
        mapRemoteDataSource: MapRemoteDataSource
    ): MapRepository {
        return MapRepositoryImpl(mapRemoteDataSource)
    }
}
