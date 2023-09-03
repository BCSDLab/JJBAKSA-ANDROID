package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName

data class MyReviewShopsResp(
    @SerializedName("content")
    val content: List<MyReviewShopsContentResp>? = emptyList()
)
