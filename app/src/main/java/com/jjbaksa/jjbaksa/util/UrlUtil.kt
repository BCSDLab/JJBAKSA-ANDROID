package com.jjbaksa.jjbaksa.util

import android.net.Uri
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UrlUtil {
    fun makeNaverMapUrl(
        latitude: String,
        longitude: String,
        packageName: String,
        zoom: Int = 20
    ): Uri {
        return Uri.parse(
            "nmap://map?" +
                "lat=$latitude" +
                "&lng=$longitude" +
                "&zoom=$zoom}" +
                "&appname=$packageName"
        )
    }

    fun makeKakaoMapUrl(latitude: String, longitude: String): Uri {
        return Uri.parse(
            "kakaomap://look?p=$latitude,$longitude"
        )
    }

    fun makeMarketURl(packageName: String): Uri {
        return Uri.parse("market://details?id=$packageName")
    }
}
