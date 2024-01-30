package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.user.UserResp

data class FollowResp(
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("follower")
    val follower: UserResp? = UserResp(),
    @SerializedName("id")
    val id: Long? = 0,
)
