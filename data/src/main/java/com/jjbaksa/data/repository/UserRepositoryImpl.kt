package com.jjbaksa.data.repository

import com.jjbaksa.data.SUCCESS
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.PasswordAndNicknameReq
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
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

    override suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean,
        onResult: (LoginResult) -> Unit
    ) {
        val response = userRemoteDataSource.postLogin(LoginReq(account, password))

        if (response != null) {
            if (response.isSuccessful) {
                if (response.body()?.code == SUCCESS) {
                    if (isAutoLogin) {
                        userLocalDataSource.saveAccessToken(response.body()!!.accessToken)
                        userLocalDataSource.saveRefreshToken(response.body()!!.refreshToken)
                        userLocalDataSource.saveAccount(account)
                        userLocalDataSource.savePassword(password)
                        userLocalDataSource.saveAutoLogin(isAutoLogin)
                    }
                    onResult(LoginResult(isSuccess = true))
                } else {
                    onResult(LoginResult(erroMessage = response.body()!!.errorMessage))
                }
            } else {
                var errorBodyJson = "${response.errorBody()!!.string()}"
                val errorBody = RespMapper.errorMapper(errorBodyJson)
                onResult(LoginResult(erroMessage = errorBody.errorMessage))
            }
        }
    }

    override suspend fun checkAuthEmail(email: String): FormatResp {
        val result = userRemoteDataSource.checkAuthEmail(email)
        return if (result.isSuccessful) {
            FormatResp(result.isSuccessful, null, result.code())
        } else {
            val errorBodyJson = result.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(result.isSuccessful, errorBody.errorMessage, result.code())
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
            FormatResp(response.isSuccessful, null, response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, response.code())
        }
    }

    override suspend fun findAccount(email: String, code: String): FormatResp {
        val response = userRemoteDataSource.findAccount(email, code)
        return if (response.isSuccessful && response.code() == 200) {
            FormatResp(response.isSuccessful, response.body()?.account.toString(), response.code())
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
            FormatResp(response.isSuccessful, null, response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, response.code())
        }
    }

    override suspend fun setNewPassword(password: String): FormatResp {
        val item = PasswordAndNicknameReq(password, null)
        val token = userLocalDataSource.getAuthPasswordToken().ifEmpty { userLocalDataSource.getAccessToken() }
        val response = userRemoteDataSource.setNewPassword(
            "Bearer $token",
            item
        )
        return if (response.isSuccessful && response.code() == 200) {
            FormatResp(response.isSuccessful, null, response.code())
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            FormatResp(response.isSuccessful, errorBody.errorMessage, response.code())
        }
    }

    override suspend fun me(): RespResult<Boolean> {
        val response = userRemoteDataSource.me()
        return if (response.isSuccessful) {
            userLocalDataSource.saveNickname(response.body()?.nickname ?: "")
            userLocalDataSource.saveFollowers(response.body()?.userCountResp?.friendCount ?: 0)
            userLocalDataSource.saveProfileImage(response.body()?.profileImage?.path ?: "")
            RespResult.Success(response.isSuccessful)
        } else {
            val errorBodyJson = response.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
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

    override fun getPassword(): String {
        return userLocalDataSource.getPassword()
    }

    override fun getAccessToken(): String {
        return userLocalDataSource.getAccessToken()
    }
}
