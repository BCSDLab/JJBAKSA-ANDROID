package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.model.user.User

data class FollowRequest(
    @SerializedName("follower")
    val follower: User = User(),
    @SerializedName("user")
    val user: User = User(),
    @SerializedName("id") val id: Long = 0,
)
