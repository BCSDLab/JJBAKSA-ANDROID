package com.jjbaksa.data.model.pin

import com.google.gson.annotations.SerializedName

data class ShopDetailResp(
    @SerializedName("placeId")
    val placeId: String? = "",
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("lastReviewDate")
    val lastReviewDate: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("lat")
    val lat: Double? = 0.0,
    @SerializedName("lng")
    val lng: Double? = 0.0,
    @SerializedName("ratingCount")
    val ratingCount: String? = "",
    @SerializedName("photos")
    val photos: List<String>? = emptyList(),
)
