package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName

data class FollowerslistResp(
    @SerializedName("content")
    val content: List<FollowContentResp> = listOf(),

)
