package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class ShopRates(
    @SerializedName("totalRating")
    val totalRating: Int = 0,
    @SerializedName("ratingCount")
    val ratingCount: Int = 0,
)
