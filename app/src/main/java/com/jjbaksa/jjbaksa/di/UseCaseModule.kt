package com.jjbaksa.jjbaksa.di

import com.jjbaksa.domain.repository.KakaoLoginRepository
import com.jjbaksa.domain.usecase.HandleKakaoLoginUseCase
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
    fun provideHandleKakaoLoginUseCase(kakaoLoginRepository: KakaoLoginRepository): HandleKakaoLoginUseCase {
        return HandleKakaoLoginUseCase(
            kakaoLoginRepository = kakaoLoginRepository
        )
    }
}
