package com.jjbaksa.data.model

import com.google.gson.annotations.SerializedName

data class CoordinateDto(
    @SerializedName("lat")
    val lat: Double? = 0.0,
    @SerializedName("lng")
    val lng: Double? = 0.0,
)
