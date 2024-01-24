package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName

data class ReviewShopContentDTO(
    @SerializedName("shopId")
    val shopId: Int? = 0,
    @SerializedName("placeId")
    val placeId: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("scrap")
    val scrap: Int? = 0,
    @SerializedName("photos")
    val photos: List<String>? = emptyList()
)
