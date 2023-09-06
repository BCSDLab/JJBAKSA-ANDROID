package com.jjbaksa.domain.model.user

data class SignUpReq(
    val account: String,
    val email: String,
    val nickname: String,
    val password: String
)
