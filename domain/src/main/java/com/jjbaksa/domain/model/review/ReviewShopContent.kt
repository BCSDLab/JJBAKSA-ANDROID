package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class ReviewShopContent(
    @SerializedName("shopId")
    val shopId: Int = 0,
    @SerializedName("placeId")
    val placeId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("photos")
    val photos: List<String> = emptyList()
)
