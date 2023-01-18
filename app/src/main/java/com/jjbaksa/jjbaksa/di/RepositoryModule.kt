package com.jjbaksa.jjbaksa.di

import com.jjbaksa.data.datasource.local.FindIdLocalDataSource
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.FindIdRemoteDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.repository.FindIdRepositoryImpl
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.data.repository.UserRepositoryImpl
import com.jjbaksa.domain.repository.FindIdRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideFindIdRepository(
        findIdRemoteDataSource: FindIdRemoteDataSource,
        findIdLocalDataSource: FindIdLocalDataSource
    ): FindIdRepository {
        return FindIdRepositoryImpl(findIdRemoteDataSource, findIdLocalDataSource)
    }
}
