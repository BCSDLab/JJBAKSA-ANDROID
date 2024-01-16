package com.jjbaksa.domain.model.inquiry

import com.google.gson.annotations.SerializedName

data class Inquiry(
    @SerializedName("content")
    val content: List<InquiryContent> = emptyList()
)
