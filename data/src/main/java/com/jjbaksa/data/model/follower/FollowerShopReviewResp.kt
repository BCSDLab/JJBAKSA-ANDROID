package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName

data class FollowerShopReviewResp(
    @SerializedName("content")
    val content: List<FollowerShopReviewContentDTO>? = emptyList()
)
