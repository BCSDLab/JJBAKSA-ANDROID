package com.jjbaksa.data.model.shop

import com.google.gson.annotations.SerializedName

data class ShopInfoResp(
    @SerializedName("placeId")
    val placeId: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("photos")
    val photos: List<String>?,
    @SerializedName("formattedAddress")
    val formattedAddress: String?,
    @SerializedName("coordinate")
    val coordinate: CoordinateResp?,
    @SerializedName("formattedPhoneNumber")
    val formattedPhoneNumber: String?,
    @SerializedName("todayPeriod")
    val todayPeriod: PeriodResp?,
)
