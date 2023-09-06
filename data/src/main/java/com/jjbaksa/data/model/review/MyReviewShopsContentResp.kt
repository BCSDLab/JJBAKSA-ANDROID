package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName

data class MyReviewShopsContentResp(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("shopPlaceId")
    val shopPlaceId: String? = "",
    @SerializedName("rate")
    val rate: Int? = 0,
    @SerializedName("userReviewResponse")
    val userReviewResponse: UserReviewResp? = UserReviewResp()
)
