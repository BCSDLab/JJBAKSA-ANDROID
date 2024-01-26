package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName

data class FollowReq(
    @SerializedName("userAccount")
    val userAccount: String? = ""
)
