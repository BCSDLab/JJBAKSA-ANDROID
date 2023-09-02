package com.jjbaksa.data.model.post

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.post.dto.PostContentDTO
import com.jjbaksa.domain.ErrorResp

data class PostResp(
    @SerializedName("content")
    val content: List<PostContentDTO>? = emptyList()
) : ErrorResp()
