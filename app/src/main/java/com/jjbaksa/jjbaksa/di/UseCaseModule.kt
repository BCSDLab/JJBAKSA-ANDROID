package com.jjbaksa.jjbaksa.di

import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.usecase.GetTrendingKeywordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetTrendingKeywordUseCase(
        shopRepository: ShopRepository
    ): GetTrendingKeywordUseCase {
        return GetTrendingKeywordUseCase(shopRepository)
    }
}
