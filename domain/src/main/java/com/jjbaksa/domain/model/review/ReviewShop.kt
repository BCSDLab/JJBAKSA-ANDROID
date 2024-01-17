package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class ReviewShop(
    @SerializedName("content")
    val content: List<ReviewShopContent> = emptyList()
)
