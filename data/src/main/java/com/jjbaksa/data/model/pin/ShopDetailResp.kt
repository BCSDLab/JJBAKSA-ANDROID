package com.jjbaksa.data.model.pin

import com.google.gson.annotations.SerializedName

data class ShopDetailResp(
    @SerializedName("shopId")
    val shopId: Int? = 0,
    @SerializedName("placeId")
    val placeId: String? = "",
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("totalRating")
    val totalRating: Int? = 0,
    @SerializedName("ratingCount")
    val ratingCount: Int? = 0,
    @SerializedName("scrap")
    val scrap: Boolean? = false,
    @SerializedName("photos")
    val photos: List<String>? = emptyList(),
)
