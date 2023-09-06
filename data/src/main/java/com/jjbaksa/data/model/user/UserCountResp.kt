package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName

data class UserCountResp(
    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("friendCount")
    val friendCount: Int? = 0,
    @SerializedName("reviewCount")
    val reviewCount: Int? = 0
)
