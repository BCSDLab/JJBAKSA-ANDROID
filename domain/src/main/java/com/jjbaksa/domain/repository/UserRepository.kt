package com.jjbaksa.domain.repository

import com.jjbaksa.data.resp.user.SignUpReq

interface UserRepository {
    suspend fun postSignUp(signUpReq: SignUpReq)
}
