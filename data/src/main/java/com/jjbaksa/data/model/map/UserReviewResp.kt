package com.jjbaksa.data.model.map

import com.google.gson.annotations.SerializedName

data class UserReviewResp(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("account")
    val account: String? = "",
    @SerializedName("nickname")
    val nickname: String? = ""
)
