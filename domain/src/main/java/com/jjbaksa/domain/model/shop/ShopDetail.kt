package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class ShopDetail(
    @SerializedName("shopId")
    val shopId: Int = 0,
    @SerializedName("placeId")
    val placeId: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("totalRating")
    val totalRating: Int = 0,
    @SerializedName("ratingCount")
    val ratingCount: Int = 0,
    @SerializedName("scrap")
    val scrap: Int = 0,
    @SerializedName("photos")
    val photos: List<String> = emptyList(),
    @SerializedName("formattedAddress")
    val formattedAddress: String = "",
    @SerializedName("formattedPhoneNumber")
    val formattedPhoneNumber: String = "",
    @SerializedName("openNow")
    val openNow: Boolean = false,
    @SerializedName("period")
    val period: List<Period> = emptyList(),
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0
)
