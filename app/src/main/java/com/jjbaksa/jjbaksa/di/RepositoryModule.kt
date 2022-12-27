package com.jjbaksa.jjbaksa.di

import com.jjbaksa.data.datasource.local.ShopLocalDataSource
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.LocationRemoteDataSource
import com.jjbaksa.data.datasource.remote.ShopRemoteDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.repository.LocationRepositoryImpl
import com.jjbaksa.data.repository.ShopRepositoryImpl
import com.jjbaksa.data.repository.UserRepositoryImpl
import com.jjbaksa.domain.repository.LocationRepository
import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.repository.UserRepository
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
    fun provideShopRepository(
        shopRemoteDataSource: ShopRemoteDataSource,
        shopLocalDataSource: ShopLocalDataSource,
    ): ShopRepository {
        return ShopRepositoryImpl(shopRemoteDataSource, shopLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(
        locationRemoteDataSource: LocationRemoteDataSource
    ): LocationRepository {
        return LocationRepositoryImpl(locationRemoteDataSource)
    }
}
