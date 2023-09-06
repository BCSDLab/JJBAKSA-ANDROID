package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.model.user.LoginReq
import com.jjbaksa.domain.model.user.PasswordAndNicknameReq
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.model.user.SignUpResp
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import retrofit2.Response

interface UserDataSource {
    suspend fun getUserMe(): Response<UserResp>
    suspend fun postLogin(loginReq: LoginReq): Response<LoginResp>
    suspend fun postUserEmailId(email: String): Response<Unit>
    suspend fun getUserId(email: String, code: String): Response<UserResp>
    suspend fun postUserEmailPassword(id: String, email: String): Response<Unit>
    suspend fun postUserPassword(findPasswordReq: FindPasswordReq): Response<String>
    suspend fun patchUserMe(
        token: String,
        passwordAndNicknameReq: PasswordAndNicknameReq
    ): Response<UserResp>

    suspend fun postUserCheckPassword(password: String): Response<UserResp>
    suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp>?
    suspend fun clearDataStore()
    suspend fun checkAccountAvailable(account: String): Response<Unit>
    suspend fun postUserWithdrawReason(withdrawalReason: WithdrawalReasonReq): Response<Unit>
    suspend fun deleteUserMe(): Response<Unit>
    suspend fun setNewNickname(item: PasswordAndNicknameReq): Response<UserResp>
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveAccount(account: String)
    suspend fun saveNickname(nickname: String)
    suspend fun saveFollowers(followers: Int)
    suspend fun saveReviews(reviews: Int)
    suspend fun saveProfileImage(image: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun saveAutoLogin(isAutoLogin: Boolean)
    suspend fun saveAuthPasswordToken(passwordToken: String)
    fun getAutoLoginFlag(): Boolean
    fun getAccount(): String
    fun getNickname(): String
    fun getFollowers(): Int
    fun getReviews(): Int
    fun getProfileImage(): String
    fun getAccessToken(): String
    fun getAuthPasswordToken(): String
}
