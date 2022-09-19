package com.jjbaksa.data.repository

import android.content.Context
import android.util.Log
import com.jjbaksa.domain.repository.KakaoLoginRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginRepositoryImpl(val context: Context) : KakaoLoginRepository {

    companion object {
        const val TAG = "KakaoLoginRepositoryImpl"
    }

    override fun handleKakaoLogin() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d(TAG, "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                Log.d(TAG, "토큰 정보 보기 성공", error)
            }

        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.e(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            }

        }


        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}