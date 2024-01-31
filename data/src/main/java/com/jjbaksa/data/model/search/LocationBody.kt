package com.jjbaksa.data.model.search

import com.google.gson.annotations.SerializedName

data class LocationBody(
    @SerializedName("lat") val lat: Double? = 0.0,
    @SerializedName("lng") val lng: Double? = 0.0
)
