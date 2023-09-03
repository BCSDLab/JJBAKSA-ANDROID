package com.jjbaksa.domain.model.user

data class FindPasswordReq(
    val account: String,
    val email: String,
    val code: String
)
