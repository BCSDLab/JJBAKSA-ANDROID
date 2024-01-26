package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName

data class FollowRequestCheckResp(
    @SerializedName("content")
    val content: FollowContentResp = FollowContentResp()

)
