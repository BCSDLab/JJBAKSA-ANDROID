package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class LoginReq(
    @SerializedName("account")
    val account: String,
    @SerializedName("password")
    val password: String
)
