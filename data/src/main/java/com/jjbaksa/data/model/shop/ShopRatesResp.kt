package com.jjbaksa.data.model.shop

import com.google.gson.annotations.SerializedName

data class ShopRatesResp(
    @SerializedName("totalRating")
    val totalRating: Int?,
    @SerializedName("ratingCount")
    val ratingCount: Int?,
)