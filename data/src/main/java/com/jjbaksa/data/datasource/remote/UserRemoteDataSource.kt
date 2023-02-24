package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.UserDataSource
import com.jjbaksa.data.model.findid.FindIdResp
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.LoginReq
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

    override suspend fun checkAccountAvailable(account: String): Response<Unit> {
        return noAuthApi.checkIdAvailable(account)
    }

    override suspend fun postLogin(loginReq: LoginReq): Response<LoginResp>? {
        return noAuthApi.login(loginReq)
    }

    override suspend fun checkAuthEmail(email: String): Response<Unit> {
        return noAuthApi.getEmailCodeNumber(email)
    }

    override suspend fun findAccount(email: String, code: String): Response<FindIdResp> {
        return noAuthApi.findId(email, code)
    }

    override suspend fun findPassword(findPasswordReq: FindPasswordReq): Response<String> {
        return noAuthApi.findPassword(findPasswordReq)
    }

    override suspend fun changeUserPassword(password: String): Response<UserResp> {
        return authApi.changePassword(password)
    }

    override suspend fun saveAccessToken(accessToken: String) {
    }

    override suspend fun saveAccount(account: String) {
    }

    override suspend fun savePassword(password: String) {
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
    }

    override suspend fun saveAutoLogin(isAutoLogin: Boolean) {
    }

    override suspend fun saveAuthPasswordToken(passwordToken: String) {
    }

    suspend fun me(): Response<UserResp> {
        return authApi.userMe()
    }

    override fun getAutoLoginFlag(): Boolean {
        return false
    }

    override fun getAcount(): String {
        return ""
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getAccessToken(): String {
        return ""
    }
}
