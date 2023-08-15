package com.jjbaksa.data.model.map

import com.google.gson.annotations.SerializedName

data class MyReviewResp(
    @SerializedName("content")
    val content: List<MyReviewContentDTO>? = emptyList()
)
