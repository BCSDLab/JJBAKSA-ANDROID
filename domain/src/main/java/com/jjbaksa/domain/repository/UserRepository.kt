package com.jjbaksa.domain.repository

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp

interface UserRepository {
    suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp?
    suspend fun checkAccountAvailable(account: String): RespResult<Boolean>
    suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean,
        onResult: (LoginResult) -> Unit
    )
    suspend fun emailAuthenticate(email: String): Boolean
    suspend fun kakaoLogin(): String
    suspend fun me()
    fun getAutoLoginFlag(): Boolean
    fun getAccount(): String
    fun getPasswrod(): String
    fun getAccessToken(): String
}
