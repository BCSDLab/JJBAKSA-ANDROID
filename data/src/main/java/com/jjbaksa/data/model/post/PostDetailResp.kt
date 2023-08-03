package com.jjbaksa.data.model.post

import com.google.gson.annotations.SerializedName

data class PostDetailResp(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("createdAt")
    val createdAt: String? = ""
)
