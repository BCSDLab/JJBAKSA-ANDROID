package com.jjbaksa.data.repository

import com.jjbaksa.data.NOID
import com.jjbaksa.data.SUCCESS
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
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
                when(errorBody.code) {
                    NOID -> {
                        onResult(LoginResult(erroMessage = errorBody.code.toString()))
                    }
                }
                onResult(LoginResult(erroMessage = errorBody.errorMessage))
            }
        }
    }

    override suspend fun me() {
    }

    override fun getAutoLoginFlag(): Boolean {
        return userLocalDataSource.getAutoLoginFlag()
    }

    override fun getAccount(): String {
        return userLocalDataSource.getAcount()
    }

    override fun getPasswrod(): String {
        return userLocalDataSource.getPassword()
    }

    override fun getAccessToken(): String {
        return userLocalDataSource.getAccessToken()
    }
}
