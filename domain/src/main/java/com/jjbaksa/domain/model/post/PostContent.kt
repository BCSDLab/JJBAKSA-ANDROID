package com.jjbaksa.domain.model.post

import com.google.gson.annotations.SerializedName

data class PostContent(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("createdAt")
    val createdAt: String = ""
)
