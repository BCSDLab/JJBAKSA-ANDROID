package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class ShopInfo(
    @SerializedName("placeId")
    val placeId: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("photos")
    val photos: List<String> = emptyList(),
    @SerializedName("formattedAddress")
    val formattedAddress: String = "",
    @SerializedName("coordinate")
    val coordinate: Coordinate = Coordinate(),
    @SerializedName("formattedPhoneNumber")
    val formattedPhoneNumber: String = "",
    @SerializedName("todayPeriod")
    val todayPeriod: Period = Period(),
)