package com.jjbaksa.domain.model.search

import com.google.gson.annotations.SerializedName

data class Shop(
    @SerializedName("dist")
    var dist: Double = 0.0,
    @SerializedName("category")
    var category: String = "",
    @SerializedName("formattedAddress")
    val formattedAddress: String = "",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("openNow")
    val openNow: Boolean = false,
    @SerializedName("photoToken")
    val photoToken: String = "",
    @SerializedName("placeId")
    val placeId: String = "",
    @SerializedName("ratingCount")
    val ratingCount: Int = 0,
    @SerializedName("totalRating")
    val totalRating: Int = 0,
    @SerializedName("type")
    var type: Int = 1
)
