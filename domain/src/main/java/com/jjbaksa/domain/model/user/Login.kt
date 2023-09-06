package com.jjbaksa.domain.model.user

data class Login(
    val accessToken: String = "",
    val refreshToken: String = "",
    var errorMessage: String = "",
    val isSuccess: Boolean = false
)
