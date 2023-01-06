package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import retrofit2.Response

interface UserDataSource {
    suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp>?
    suspend fun checkAccountAvailable(account: String): Response<Unit>
    suspend fun postLogin(loginReq: LoginReq): Response<LoginResp>?
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveAccount(account: String)
    suspend fun savePassword(password: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun saveAutoLogin(isAutoLogin: Boolean)
    fun getAutoLoginFlag(): Boolean
    fun getAcount(): String
    fun getPassword(): String
    fun getAccessToken(): String
}
