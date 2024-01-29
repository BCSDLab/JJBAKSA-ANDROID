package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.model.user.User

data class followRequestRecived(
    @SerializedName("content")
    val content: List<FollowContent> = listOf(),
)
