package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName

data class UserProfileResp(
    @SerializedName("id")
    var id: Int,
    @SerializedName("originalName")
    var originalName: String,
    @SerializedName("path")
    var path: String,
    @SerializedName("url")
    var url: String
)
