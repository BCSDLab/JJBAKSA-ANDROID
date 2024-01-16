package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class SignUpReq(
    @SerializedName("account")
    val account: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("password")
    val password: String
)
