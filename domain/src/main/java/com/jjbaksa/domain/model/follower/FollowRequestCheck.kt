package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName

data class FollowRequestCheck(
    @SerializedName("page")
    val page: Int? = 0 ,
    @SerializedName("pageSize")
    val pageSize: Int? = 0
)
