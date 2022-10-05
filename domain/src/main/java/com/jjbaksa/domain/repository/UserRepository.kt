package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.user.SignUpReq

interface UserRepository {
    suspend fun postSignUp(signUpReq: SignUpReq)
    suspend fun checkIdAvailable(account: String): Boolean
}
