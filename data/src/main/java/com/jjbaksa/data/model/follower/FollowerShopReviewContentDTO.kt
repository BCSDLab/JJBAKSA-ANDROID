package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.map.UserReviewResp

data class FollowerShopReviewContentDTO(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("rate")
    val rate: Int? = 0,
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("userReviewResponse")
    val userReviewResponse: UserReviewResp? = UserReviewResp(),
    @SerializedName("shopPlaceId")
    val shopPlaceId: String? = ""
)
