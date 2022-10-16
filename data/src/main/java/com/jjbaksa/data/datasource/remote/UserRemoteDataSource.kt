package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.UserDataSource
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : UserDataSource {
    override suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp> {
        return noAuthApi.signUp(signUpReq)
    }

    override suspend fun checkAccountAvailable(account: String): Response<String> {
        return noAuthApi.checkIdAvailable(account)
    }
}
