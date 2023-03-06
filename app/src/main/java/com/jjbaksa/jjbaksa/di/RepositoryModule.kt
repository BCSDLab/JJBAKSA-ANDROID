package com.jjbaksa.jjbaksa.di

import android.content.ContentResolver
import android.content.Context
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.repository.ImageRepositoryImpl
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.data.repository.UserRepositoryImpl
import com.jjbaksa.domain.repository.ImageRepository
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
}
