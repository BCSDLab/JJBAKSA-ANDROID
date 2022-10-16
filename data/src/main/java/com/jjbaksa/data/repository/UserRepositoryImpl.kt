package com.jjbaksa.data.repository

import android.util.Log
import com.jjbaksa.data.SUCCESS
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.mapper.CheckAccountAvailableMapper
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginReq
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpResp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp? {
        val resp = userRemoteDataSource.postSignUp(signUpReq)
        return resp.body()
    }

    override suspend fun checkAccountAvailable(account: String): Boolean {
        val result = userRemoteDataSource.checkAccountAvailable(account)
        return CheckAccountAvailableMapper.mapToBoolean(result.code())
    }

    override suspend fun postLogin(
        account: String,
        password: String,
        isAutoLogin: Boolean,
        onResult: (LoginResult) -> Unit
    ) {

        val response = userRemoteDataSource.postLogin(LoginReq(account, password))
        if (response!!.isSuccessful) {
            if (response.body()!!.code == SUCCESS) {
                if (isAutoLogin) {
                    userLocalDataSource.saveAccessToken(response.body()!!.accessToken)
                    userLocalDataSource.saveRefreshToken(response.body()!!.refreshToken)
                    userLocalDataSource.saveAccount(account)
                    userLocalDataSource.savePassword(password)
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
