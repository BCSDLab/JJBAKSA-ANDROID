package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.model.user.User

data class FollowerList(
    @SerializedName("content")
    val content: List<User> = emptyList()
)
