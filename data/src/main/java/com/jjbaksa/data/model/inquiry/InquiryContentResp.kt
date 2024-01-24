package com.jjbaksa.data.model.inquiry

import com.google.gson.annotations.SerializedName

data class InquiryContentResp(
    @SerializedName("answer")
    val answer: String? = "",
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("createdBy")
    val createdBy: String? = "",
    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("inquiryImages")
    val inquiryImages: List<InquiryImagesResp>? = emptyList(),
    @SerializedName("isSecreted")
    val isSecreted: Int? = 0,
    @SerializedName("title")
    val title: String? = ""
)
