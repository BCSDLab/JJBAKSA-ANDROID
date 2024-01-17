package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class FindPasswordReq(
    @SerializedName("account")
    val account: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
)
