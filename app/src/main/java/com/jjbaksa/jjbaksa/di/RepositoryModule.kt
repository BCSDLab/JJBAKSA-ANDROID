package com.jjbaksa.jjbaksa.di

import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import android.content.Context
import com.jjbaksa.jjbaksa.HandleKakaoLoginUseCase
import com.jjbaksa.jjbaksa.KakaoLoginRepository
import com.jjbaksa.jjbaksa.KakaoLoginRepositoryImpl
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
        userLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
    }
}

    @Singleton
    @Provides
    fun provideKakaoLoginRepository(@ApplicationContext context: Context): KakaoLoginRepository {
        return KakaoLoginRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideHandleKakaoLoginUseCase(kakaoLoginRepository: KakaoLoginRepository): HandleKakaoLoginUseCase{
        return HandleKakaoLoginUseCase(
            kakaoLoginRepository = kakaoLoginRepository
        )
    }

}