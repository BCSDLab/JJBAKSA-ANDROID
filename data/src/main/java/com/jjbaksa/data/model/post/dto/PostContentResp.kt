package com.jjbaksa.data.model.post.dto

import com.google.gson.annotations.SerializedName

data class PostContentResp(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("createdAt")
    val createdAt: String? = ""
)
