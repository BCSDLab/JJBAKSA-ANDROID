package com.jjbaksa.data.model.inquiry.dto

import com.google.gson.annotations.SerializedName

data class InquiryImagesDTO(
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("originalName")
    val originalName: String?,
    @SerializedName("path")
    val path: String?,
)
