package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class MyReviewShopsContent(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("rate")
    val rate: Int = 0,
    @SerializedName("createdAt")
    val createdAt: String = ""
)
