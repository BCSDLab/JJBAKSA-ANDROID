package com.jjbaksa.data.model.pin

import com.google.gson.annotations.SerializedName

data class RateDto(
    @SerializedName("totalRating")
    val totalRating: Int? = 0,
    @SerializedName("ratingCount")
    val ratingCount: Int? = 0,
)
