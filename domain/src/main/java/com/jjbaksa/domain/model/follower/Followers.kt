package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName

data class Followers(
    @SerializedName("content")
    val content: List<FollowContent> = listOf(),
)
