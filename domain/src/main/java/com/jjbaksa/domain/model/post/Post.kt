package com.jjbaksa.domain.model.post

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("content")
    val content: List<PostContent> = emptyList()
)
