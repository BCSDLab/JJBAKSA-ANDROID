package com.jjbaksa.domain.model.user

data class SignUpResp(
    val account: String,
    val email: String,
    val id: Long,
    val nickname: String,
    val oauthType: String,
    val userType: String
)
