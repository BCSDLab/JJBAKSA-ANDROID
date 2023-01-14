package com.jjbaksa.data.mapper

object SocialLoginUrlMapper {
    fun urlMapper(response: String): String {
        val urlIndex = response.indexOf("url") + 4
        val kakaoLoginUrl = response.substring(urlIndex, response.length - 1)
        return kakaoLoginUrl
    }
}