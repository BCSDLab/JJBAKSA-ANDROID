package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class UserCount(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("friendCount")
    val friendCount: Int = 0,
    @SerializedName("reviewCount")
    val reviewCount: Int = 0
)
