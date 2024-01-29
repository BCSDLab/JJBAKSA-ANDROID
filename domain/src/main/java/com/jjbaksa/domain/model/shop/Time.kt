package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class Time(
    @SerializedName("hour")
    val hour: Int = 0,
    @SerializedName("minute")
    val minute: Int = 0
)