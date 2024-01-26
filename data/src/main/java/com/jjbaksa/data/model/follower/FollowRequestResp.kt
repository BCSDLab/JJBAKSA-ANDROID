package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.user.UserCountResp
import com.jjbaksa.data.model.user.UserResp


data class FollowRequestResp  (
    @SerializedName("follower")
    val follower: UserResp? = UserResp(),
    @SerializedName("user")
    val user: UserResp? = UserResp(),
    @SerializedName("id") val id: Long = 0,
)
