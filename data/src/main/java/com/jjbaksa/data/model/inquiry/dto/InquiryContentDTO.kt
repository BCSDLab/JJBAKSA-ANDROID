package com.jjbaksa.data.model.inquiry.dto

import com.google.gson.annotations.SerializedName

data class InquiryContentDTO(
    @SerializedName("answer")
    val answer: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("createdBy")
    val createdBy: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("inquiryImages")
    val inquiryImages: List<InquiryImagesDTO>?,
    @SerializedName("isSecreted")
    val isSecreted: Int?,
    @SerializedName("title")
    val title: String?
)
