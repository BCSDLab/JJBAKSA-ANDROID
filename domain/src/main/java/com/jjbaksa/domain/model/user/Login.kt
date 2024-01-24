package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("accessToken")
    val accessToken: String = "",
    @SerializedName("refreshToken")
    val refreshToken: String = "",
    @SerializedName("errorMessage")
    var errorMessage: String = "",
    @SerializedName("isSuccess")
    val isSuccess: Boolean = false
)
