package com.jjbaksa.data.repository

import android.net.Uri
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.mapper.FormDataUtil
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.data.mapper.user.toLoginResult
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.domain.model.user.LoginReq
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.PasswordAndNicknameReq
import com.jjbaksa.domain.resp.user.WithdrawalReasonReq
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun getUserMe(): Flow<Result<User>> {
        return apiCall(
            call = { userRemoteDataSource.getUserMe() },
            remoteData = {
                if (it.isSuccessful) {
                    val user = it.body()?.toUser() ?: User()
                    userLocalDataSource.saveNickname(user.nickname)
                    userLocalDataSource.saveFollowers(user.userCountResponse.friendCount)
                    userLocalDataSource.saveReviews(user.userCountResponse.reviewCount)
                    userLocalDataSource.saveProfileImage(user.profileImage.url.toString())
                }
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toUser() ?: User()
                } else {
                    User()
                }
            }
        )
    }

    override suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean
    ): Flow<Result<Login>> {
        return apiCall(
            call = { userRemoteDataSource.postLogin(LoginReq(account, password)) },
            remoteData = {
                if (it.isSuccessful) {
                    userLocalDataSource.clearDataStore()
                    val loginResponse = it.body()?.toLoginResult() ?: Login()
                    userLocalDataSource.saveAccessToken(loginResponse.accessToken)
                    userLocalDataSource.saveRefreshToken(loginResponse.refreshToken)
                    userLocalDataSource.saveAccount(account)
                    userLocalDataSource.saveAutoLogin(isAutoLogin)
                }
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toLoginResult() ?: Login()
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    Login(errorMessage = errorResult.errorMessage)
                }
            }
        )
    }

    override suspend fun postUserEmailId(
        email: String,
        onError: (String) -> Unit
    ): Flow<Result<Boolean>> {
        return apiCall(
            call = { userRemoteDataSource.postUserEmailId(email) },
            mapper = {
                if (it.isSuccessful) {
                    true
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    false
                }
            }
        )
    }

    override suspend fun getUserId(
        email: String,
        code: String,
        onError: (String) -> Unit
    ): Flow<Result<String>> {
        return apiCall(
            call = { userRemoteDataSource.getUserId(email, code) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.account ?: ""
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    ""
                }
            }
        )
    }

    override suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp? {
        val resp = userRemoteDataSource.postSignUp(signUpReq)
        return resp.body()
    }

    override suspend fun checkAccountAvailable(account: String): RespResult<Boolean> {
        val result = userRemoteDataSource.checkAccountAvailable(account)
        return if (result.isSuccessful) {
            RespResult.Success(result.isSuccessful)
        } else {
            val errorBodyJson = result.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
    }

    override suspend fun checkPassword(password: String): FormatResp {
        val response = userRemoteDataSource.checkPassword(
            "Bearer " + userLocalDataSource.getAccessToken(),
            password
        )
        return if (response.isSuccessful && response.code() == 200) {
            FormatResp(response.isSuccessful, "", response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, errorBody.code)
        }
    }

    override suspend fun getPasswordVerificationCode(
        id: String,
        email: String
    ): FormatResp {
        val response = userRemoteDataSource.getPasswordVerificationCode(id, email)
        return if (response.isSuccessful && response.code() == 200) {
            FormatResp(response.isSuccessful, "", response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, response.code())
        }
    }

    override suspend fun findPassword(user: FindPasswordReq): FormatResp {
        val response = userRemoteDataSource.findPassword(user)
        return if (response.isSuccessful && response.code() == 200) {
            userLocalDataSource.saveAuthPasswordToken(response.body().toString())
            FormatResp(response.isSuccessful, "", response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, response.code())
        }
    }

    override suspend fun setNewPassword(password: String): FormatResp {
        val item = PasswordAndNicknameReq(password, null)
        val token = userLocalDataSource.getAuthPasswordToken()
            .ifEmpty { userLocalDataSource.getAccessToken() }
        val response = userRemoteDataSource.setNewPassword(
            "Bearer $token",
            item
        )
        return if (response.isSuccessful && response.code() == 200) {
            FormatResp(response.isSuccessful, "", response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, response.code())
        }
    }

    override suspend fun setNewNickname(nickname: String): FormatResp {
        val item = PasswordAndNicknameReq(null, nickname)
        val response = userRemoteDataSource.setNewNickname(item)
        return if (response.isSuccessful && response.code() == 200) {
            userLocalDataSource.saveNickname(nickname)
            FormatResp(response.isSuccessful, "", response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, response.code())
        }
    }

    override suspend fun editUserProfileImage(profile: String): RespResult<Boolean> {
        val fileBody = FormDataUtil.getImageBody("profile", Uri.parse(profile))
        val response = userRemoteDataSource.editUserProfileImage(fileBody)
        return if (response.isSuccessful) {
            userLocalDataSource.saveProfileImage(response.body()?.profileImage?.url ?: "")
            RespResult.Success(true)
        } else {
            RespResult.Success(false)
        }
    }

    override suspend fun saveWithdrawalReason(withdrawalReason: WithdrawalReasonReq): RespResult<Boolean> {
        val response = userRemoteDataSource.saveWithdrawalReason(withdrawalReason)
        return if (response.isSuccessful) {
            RespResult.Success(true)
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
    }

    override suspend fun deleteUser(): RespResult<Boolean> {
        val response = userRemoteDataSource.deleteUser()
        return if (response.isSuccessful && response.code() == 204) {
            userLocalDataSource.clearDataStore()
            RespResult.Success(response.isSuccessful)
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
    }

    override suspend fun singOut() {
        userLocalDataSource.clearDataStore()
    }

    override fun getAutoLoginFlag(): Boolean {
        return userLocalDataSource.getAutoLoginFlag()
    }

    override fun getAccount(): String {
        return userLocalDataSource.getAccount()
    }

    override fun getNickname(): String {
        return userLocalDataSource.getNickname()
    }

    override fun getFollowers(): Int {
        return userLocalDataSource.getFollowers()
    }

    override fun getProfileImage(): String {
        return userLocalDataSource.getProfileImage()
    }

    override fun getAccessToken(): String {
        return userLocalDataSource.getAccessToken()
    }
}
