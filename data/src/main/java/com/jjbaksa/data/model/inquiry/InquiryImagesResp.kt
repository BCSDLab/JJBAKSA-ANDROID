package com.jjbaksa.data.model.inquiry

import com.google.gson.annotations.SerializedName

data class InquiryImagesResp(
    @SerializedName("imageUrl")
    val imageUrl: String? = "",
    @SerializedName("originalName")
    val originalName: String? = "",
    @SerializedName("path")
    val path: String? = "",
)
