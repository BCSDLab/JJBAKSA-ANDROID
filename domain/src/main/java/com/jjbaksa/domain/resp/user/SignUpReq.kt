package com.jjbaksa.data.resp.user

data class SignUpReq(
    val acctoun: String,
    val email: String,
    val nickname: String,
    val password: String
)
