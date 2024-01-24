package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class ReviewImages(
    @SerializedName("originalName")
    val originalName: String? = "",
    @SerializedName("imageUrl")
    val imageUrl: String? = ""
)
