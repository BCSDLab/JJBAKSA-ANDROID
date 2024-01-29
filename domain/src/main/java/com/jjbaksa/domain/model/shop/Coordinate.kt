package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0
)