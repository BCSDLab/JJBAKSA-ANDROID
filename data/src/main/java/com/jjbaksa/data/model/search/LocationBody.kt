package com.jjbaksa.data.model.search

import com.google.gson.annotations.SerializedName

data class LocationBody(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)
