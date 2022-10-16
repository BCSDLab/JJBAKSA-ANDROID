package com.jjbaksa.domain.resp.user

data class SignUpReq(
    val account: String,
    val email: String,
    val nickname: String,
    val password: String
)
