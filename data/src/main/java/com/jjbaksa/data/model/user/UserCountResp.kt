package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName

data class UserCountResp(
    @SerializedName("friendCount")
    var friendCount: Int,
    @SerializedName("id")
    var id: Long,
    @SerializedName("reviewCount")
    var reviewCount: Int
)
