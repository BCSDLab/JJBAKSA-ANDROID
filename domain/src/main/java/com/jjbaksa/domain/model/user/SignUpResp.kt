package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class SignUpResp(
    @SerializedName("account")
    val account: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("oauthType")
    val oauthType: String,
    @SerializedName("userType")
    val userType: String
)
