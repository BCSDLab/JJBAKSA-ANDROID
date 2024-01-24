package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class MyReviewShops(
    @SerializedName("content")
    val content: List<MyReviewShopsContent> = emptyList()
)
