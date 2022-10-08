package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.mapper.CheckAccountAvailableMapper
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.repository.UserRepository
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

    override suspend fun checkIdAvailable(account: String): Boolean {
        val result = userRemoteDataSource.checkIdAvailable(account)
        return CheckAccountAvailableMapper.mapToBoolean(result.code())
    }
}
