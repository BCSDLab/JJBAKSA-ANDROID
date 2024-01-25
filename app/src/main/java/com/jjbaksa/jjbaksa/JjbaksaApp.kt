package com.jjbaksa.jjbaksa

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatDelegate
import com.jjbaksa.data.database.PreferenceKeys
import com.jjbaksa.data.database.userDataStore
import com.jjbaksa.jjbaksa.util.MyInfo
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltAndroidApp
class JjbaksaApp : Application() {
    val appContext: Context = this
    val isDebug
        get() = isDebug(appContext)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        KakaoSdk.init(this, BuildConfig.kakao_native_app_key)
        instance = this
        val dataStore = appContext.userDataStore.data
        CoroutineScope(Dispatchers.IO).launch {
            MyInfo.autoLogin = dataStore.first()[PreferenceKeys.AUTO_LOGIN] ?: false
            MyInfo.account = dataStore.first()[PreferenceKeys.ACCOUNT] ?: ""
            MyInfo.nickname = dataStore.first()[PreferenceKeys.NICKNAME] ?: ""
            MyInfo.followers = dataStore.first()[PreferenceKeys.FOLLOWERS] ?: 0
            MyInfo.reviews = dataStore.first()[PreferenceKeys.REVIEWS] ?: 0
            MyInfo.profileImage = dataStore.first()[PreferenceKeys.IMAGE] ?: ""
            MyInfo.token = dataStore.first()[PreferenceKeys.ACCESS_TOKEN] ?: ""
        }
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
