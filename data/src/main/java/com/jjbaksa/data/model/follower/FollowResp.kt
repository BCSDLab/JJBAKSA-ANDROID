package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.user.UserCountResp
import com.jjbaksa.data.model.user.UserResp

data class FollowResp (
    @SerializedName("follower")
    val follower: UserResp? = UserResp(),
    @SerializedName("userCountResponse")
    val followerCountResp: UserCountResp? = UserCountResp()
)