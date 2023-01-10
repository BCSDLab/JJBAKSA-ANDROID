package com.jjbaksa.jjbaksa

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JjbaksaApp : Application() {
    val appContext: Context = this
    val isDebug
        get() = isDebug(appContext)

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.kakao_native_app_key)
        instance = this
    }

    /**
     * 디버그모드인지 확인하는 함수
     */
    private fun isDebug(context: Context): Boolean {
        var debuggable = false
        val pm: PackageManager = context.packageManager
        try {
            val appinfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = 0 != appinfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return debuggable
    }
    companion object {
        lateinit var instance: JjbaksaApp
            private set
    }
}
