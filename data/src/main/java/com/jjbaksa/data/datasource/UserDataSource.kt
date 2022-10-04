package com.jjbaksa.data.datasource

import com.jjbaksa.data.resp.user.SignUpReq
import com.jjbaksa.data.resp.user.SignUpResp
import retrofit2.Response

interface UserDataSource {
    suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp>?
    suspend fun checkIdAvailable(account: String): String
}
