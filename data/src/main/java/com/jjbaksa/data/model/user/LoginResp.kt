package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.ErrorResp

data class LoginResp(
    @SerializedName("accessToken")
    val accessToken: String? = "",
    @SerializedName("refreshToken")
    val refreshToken: String? = ""
) : ErrorResp()
