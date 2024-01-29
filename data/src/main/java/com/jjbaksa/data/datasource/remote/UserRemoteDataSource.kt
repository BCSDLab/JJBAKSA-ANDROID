package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.UserDataSource
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserListResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.model.user.LoginReq
import com.jjbaksa.domain.model.user.PasswordAndNicknameReq
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.model.user.SignUpResp
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : UserDataSource {
    override suspend fun postLoginSNS(token: String, snsType: String): LoginResp =
        noAuthApi.postLoginSNS(token, snsType)

    override suspend fun getUserMe(): Response<UserResp> {
        return authApi.getUserMe()
    }

    override suspend fun postLogin(loginReq: LoginReq): Response<LoginResp> {
        return noAuthApi.postLogin(loginReq)
    }

    override suspend fun postUserEmailId(email: String): Response<Unit> {
        return noAuthApi.postUserEmailId(email)
    }

    override suspend fun getUserId(email: String, code: String): Response<UserResp> {
        return noAuthApi.getUserId(email, code)
    }

    override suspend fun postUserEmailPassword(id: String, email: String): Response<Unit> {
        return noAuthApi.postUserEmailPassword(id, email)
    }

    override suspend fun postUserPassword(findPasswordReq: FindPasswordReq): Response<String> {
        return noAuthApi.postUserPassword(findPasswordReq)
    }

    override suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp> {
        return noAuthApi.signUp(signUpReq)
    }

    override suspend fun checkAccountAvailable(account: String): Response<Unit> {
        return noAuthApi.checkIdAvailable(account)
    }

    override suspend fun postUserCheckPassword(password: String): Response<UserResp> {
        return authApi.postUserCheckPassword(password)
    }

    override suspend fun patchUserMe(
        token: String,
        passwordAndNicknameReq: PasswordAndNicknameReq
    ): Response<UserResp> {
        return noAuthApi.patchUserMe(token, passwordAndNicknameReq)
    }

    override suspend fun setNewNickname(passwordAndNicknameReq: PasswordAndNicknameReq): Response<UserResp> {
        return authApi.setUserNickname(passwordAndNicknameReq)
    }

    override suspend fun postUserWithdrawReason(withdrawalReason: WithdrawalReasonReq): Response<Unit> {
        return authApi.postUserWithdrawReason(withdrawalReason)
    }

    override suspend fun deleteUserMe(): Response<Unit> {
        return authApi.deleteUserMe()
    }

    override suspend fun postUserEmailCheck(email: String): Response<LoginResp> {
        return noAuthApi.postUserEmailCheck(email)
    }

    override suspend fun saveAccessToken(accessToken: String) {}
    override suspend fun saveAccount(account: String) {}
    override suspend fun saveNickname(nickname: String) {}
    override suspend fun saveFollowers(followers: Int) {}
    override suspend fun saveReviews(reviews: Int) {}
    override suspend fun saveProfileImage(image: String) {}
    override suspend fun saveRefreshToken(refreshToken: String) {}
    override suspend fun saveAutoLogin(isAutoLogin: Boolean) {}
    override suspend fun saveAuthPasswordToken(passwordToken: String) {}
    override suspend fun clearDataStore() {}

    suspend fun editUserProfile(profile: MultipartBody.Part): Response<UserResp> {
        return authApi.patchUserProfile(profile)
    }

    override fun getAutoLoginFlag(): Boolean {
        return false
    }

    override fun getAccount(): String {
        return ""
    }

    override fun getNickname(): String {
        return ""
    }

    override fun getFollowers(): Int {
        return 0
    }

    override fun getReviews(): Int {
        return 0
    }

    override fun getProfileImage(): String {
        return ""
    }

    override fun getAccessToken(): String {
        return ""
    }

    override fun getAuthPasswordToken(): String {
        return ""
    }


    override suspend fun getUserSearch(
        keyword: String,
        pageSize: Int?,
        cursor: Long?
    ): Response<UserListResp> {
        return authApi.getUserSearch(keyword, pageSize, cursor)
    }
}
