package com.jjbaksa.domain.model.inquiry

import com.google.gson.annotations.SerializedName

data class InquiryImages(
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("originalName")
    val originalName: String = "",
    @SerializedName("path")
    val path: String = ""
)
