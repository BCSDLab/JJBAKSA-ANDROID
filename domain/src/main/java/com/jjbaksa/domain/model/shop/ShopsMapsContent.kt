package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class ShopsMapsContent(
    @SerializedName("placeId")
    val placeId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0,
    @SerializedName("photo")
    val photo: String = ""
)
