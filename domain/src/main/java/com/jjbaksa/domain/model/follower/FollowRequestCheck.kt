package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName

data class FollowRequestRecived(
    @SerializedName("content")
    val content: List<FollowContent> = listOf(),
)
