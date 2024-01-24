package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class Open(
    @SerializedName("day")
    val day: Int = 0,
    @SerializedName("time")
    val time: Int = 0
)
