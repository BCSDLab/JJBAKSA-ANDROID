package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class PasswordAndNicknameReq(
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("nickname")
    val nickname: String? = null
)
