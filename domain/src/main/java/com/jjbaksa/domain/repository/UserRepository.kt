package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp

interface UserRepository {
    suspend fun postSignUp(signUpReq: SignUpReq): SignUpResp?
    suspend fun checkAccountAvailable(account: String): Boolean
}
