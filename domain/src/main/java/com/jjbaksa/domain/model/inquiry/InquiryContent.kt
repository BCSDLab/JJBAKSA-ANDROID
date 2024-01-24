package com.jjbaksa.domain.model.inquiry

import com.google.gson.annotations.SerializedName

data class InquiryContent(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("answer")
    val answer: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("createdBy")
    val createdBy: String = "",
    @SerializedName("inquiryImages")
    val inquiryImages: List<InquiryImages> = emptyList(),
    @SerializedName("isSecreted")
    val isSecreted: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("expandable")
    var expandable: Boolean = false
)
