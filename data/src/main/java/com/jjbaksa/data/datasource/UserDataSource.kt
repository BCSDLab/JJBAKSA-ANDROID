package com.jjbaksa.data.datasource

import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import retrofit2.Response

interface UserDataSource {
    suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp>?
    suspend fun checkAccountAvailable(account: String): Response<String>
}
