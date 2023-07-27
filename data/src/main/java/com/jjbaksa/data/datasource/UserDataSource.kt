package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.findid.FindIdResp
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.domain.resp.user.PasswordAndNicknameReq
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import com.jjbaksa.domain.resp.user.WithdrawalReasonReq
import retrofit2.Response

interface UserDataSource {
    suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp>?
    suspend fun clearDataStore()
    suspend fun checkAccountAvailable(account: String): Response<Unit>
    suspend fun postLogin(loginReq: LoginReq): Response<LoginResp>?
    suspend fun checkAuthEmail(email: String): Response<Unit>
    suspend fun checkPassword(password: String, token: String): Response<Unit>
    suspend fun getPasswordVerificationCode(id: String, email: String): Response<Unit>
    suspend fun findAccount(email: String, code: String): Response<FindIdResp>
    suspend fun findPassword(findPasswordReq: FindPasswordReq): Response<String>
    suspend fun saveWithdrawalReason(withdrawalReason: WithdrawalReasonReq): Response<Unit>
    suspend fun deleteUser(): Response<Unit>
    suspend fun setNewPassword(token: String, item: PasswordAndNicknameReq): Response<UserResp>
    suspend fun setNewNickname(item: PasswordAndNicknameReq): Response<UserResp>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveAccount(account: String)
    suspend fun saveNickname(nickname: String)
    suspend fun saveFollowers(followers: Int)
    suspend fun saveProfileImage(image: String)
    suspend fun savePassword(password: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun saveAutoLogin(isAutoLogin: Boolean)
    suspend fun saveAuthPasswordToken(passwordToken: String)
    fun getAutoLoginFlag(): Boolean
    fun getAccount(): String
    fun getNickname(): String
    fun getFollowers(): Int
    fun getProfileImage(): String
    fun getPassword(): String
    fun getAccessToken(): String
    fun getAuthPasswordToken(): String
}
