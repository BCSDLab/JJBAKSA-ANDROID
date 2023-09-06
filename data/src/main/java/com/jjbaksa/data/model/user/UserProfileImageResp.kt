package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName

data class UserProfileImageResp(
    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("originalName")
    val originalName: String? = "",
    @SerializedName("path")
    val path: String? = "",
    @SerializedName("url")
    val url: String? = ""
)
