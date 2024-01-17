package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class UserProfileImage(
    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("originalName")
    val originalName: String? = "",
    @SerializedName("path")
    val path: String? = "",
    @SerializedName("url")
    val url: String? = ""
)
