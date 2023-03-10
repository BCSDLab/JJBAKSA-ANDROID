package com.jjbaksa.domain.resp.user

data class FindPasswordReq(
    val account: String,
    val email: String,
    val code: String
)
