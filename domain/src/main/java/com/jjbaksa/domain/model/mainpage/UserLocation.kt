package com.jjbaksa.domain.model.mainpage

import com.google.gson.annotations.SerializedName

data class UserLocation(
    @SerializedName("latitude")
    val latitude: Double = 37.toDouble(),
    @SerializedName("longitude")
    val longitude: Double = 127.toDouble(),
)
