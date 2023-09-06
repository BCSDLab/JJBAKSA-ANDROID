package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName

data class ReviewImageResp(
    @SerializedName("originalName")
    val originalName: String? = "",
    @SerializedName("imageUrl")
    val imageUrl: String? = ""
)
