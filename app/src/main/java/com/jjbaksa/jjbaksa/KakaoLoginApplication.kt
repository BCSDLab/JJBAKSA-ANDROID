package com.jjbaksa.jjbaksa

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoLoginApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}