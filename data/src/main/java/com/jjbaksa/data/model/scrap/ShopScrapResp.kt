package com.jjbaksa.data.model.scrap

import com.google.gson.annotations.SerializedName

data class ShopScrapResp(
    @SerializedName("scrapId")
    val scrapId: Int? = 0,
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
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("photo")
    val photo: List<String>? = emptyList()
)
