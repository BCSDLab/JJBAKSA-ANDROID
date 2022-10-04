package com.jjbaksa.data.datasource.remote

import android.util.Log
import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.UserDataSource
import com.jjbaksa.data.resp.user.SignUpReq
import com.jjbaksa.data.resp.user.SignUpResp
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : UserDataSource {
    override suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp> {
        return noAuthApi.signUp(signUpReq)
    }

    override suspend fun checkIdAvailable(account: String): String {
        return noAuthApi.checkIdAvailable(account)
    }
}
