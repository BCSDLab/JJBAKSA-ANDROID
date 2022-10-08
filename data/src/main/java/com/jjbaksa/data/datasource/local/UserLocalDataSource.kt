package com.jjbaksa.data.datasource.local

import com.jjbaksa.data.database.UserDao
import com.jjbaksa.data.datasource.UserDataSource
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import retrofit2.Response
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {
    override suspend fun postSignUp(signUpReq: SignUpReq): Response<SignUpResp>? {
        return null
    }

    override suspend fun checkIdAvailable(account: String): Response<String> {
        TODO("Not yet implemented")
    }
}
