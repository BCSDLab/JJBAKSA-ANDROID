package com.jjbaksa.data.model.shop

import com.google.gson.annotations.SerializedName

data class CoordinateResp(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lng")
    val lng: Double?
)