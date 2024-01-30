package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.model.user.User

data class FollowContent(
    @SerializedName("follower")
    val follower: User = User(),
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("user")
    val user: User = User()
)
