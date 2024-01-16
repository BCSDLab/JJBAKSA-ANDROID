package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class FollowerReviewShops(
    @SerializedName("content")
    val content: List<FollowerReviewShopsContent> = emptyList()
)
