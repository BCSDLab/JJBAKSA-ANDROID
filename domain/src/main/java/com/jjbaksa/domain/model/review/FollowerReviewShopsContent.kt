package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class FollowerReviewShopsContent(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("rate")
    val rate: Int = 0,
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("userReviewResponse")
    val userReviewResponse: UserReview = UserReview(),
    @SerializedName("shopPlaceId")
    val shopPlaceId: String = ""
)
