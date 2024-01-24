package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName

data class FollowerReviewShopsResp(
    @SerializedName("content")
    val content: List<FollowerReviewShopsContentDTO>? = emptyList()
)
