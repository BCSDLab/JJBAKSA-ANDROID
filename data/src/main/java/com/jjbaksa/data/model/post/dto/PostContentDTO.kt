package com.jjbaksa.data.model.post.dto

import com.google.gson.annotations.SerializedName

data class PostContentDTO(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("createdAt")
    val createdAt: String? = ""
)
