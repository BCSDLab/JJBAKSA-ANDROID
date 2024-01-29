package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class Period(
    @SerializedName("close")
    val close: Time = Time(),
    @SerializedName("open")
    val open: Time = Time()
)
