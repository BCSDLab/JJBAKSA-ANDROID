package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.user.UserResp

data class FollowerListResp(
    @SerializedName("content")
    val content: List<UserResp>? = emptyList()
)
