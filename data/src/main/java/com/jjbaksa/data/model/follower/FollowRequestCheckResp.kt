package com.jjbaksa.data.model.follower

import com.google.gson.annotations.SerializedName

data class FollowRequestCheckResp(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("pageSize")
    val pageSize: Int? = 0

)
