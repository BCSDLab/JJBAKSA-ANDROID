package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.model.user.User

data class FollowContentResp(
    @SerializedName("follower")
    val follower: UserResp = UserResp(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("user")
    val user: UserResp = UserResp()
)
