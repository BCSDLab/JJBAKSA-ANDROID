package com.jjbaksa.jjbaksa.di

import android.content.Context
import com.jjbaksa.jjbaksa.ui.social.KakaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object SocialModule {
    @Provides
    fun provideKakaoService(
        @ActivityContext context: Context
    ) = KakaoService(context)
}