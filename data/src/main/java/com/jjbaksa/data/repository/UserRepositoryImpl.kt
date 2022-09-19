package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.UserRemoteDataSource
import com.jjbaksa.data.resp.user.SignUpReq
import com.jjbaksa.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postSignUp(signUpReq: SignUpReq) {
        val resp = userRemoteDataSource.postSignUp(signUpReq)
    }
}
