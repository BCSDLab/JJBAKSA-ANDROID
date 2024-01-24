package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName

data class ReviewShopResp(
    @SerializedName("content")
    val content: List<ReviewShopContentDTO>? = emptyList()
)
