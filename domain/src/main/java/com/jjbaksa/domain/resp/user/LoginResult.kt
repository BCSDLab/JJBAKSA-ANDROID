package com.jjbaksa.domain.resp.user

data class LoginResult(
    val errorMessage: String = "",
    val isSuccess: Boolean = false
)
