package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName

data class ReviewShopContentDTO(
    @SerializedName("id")
    val shopId: Int? = 0,
    @SerializedName("placeId")
    val placeId: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("photos")
    val photos: List<String>? = emptyList()
)
