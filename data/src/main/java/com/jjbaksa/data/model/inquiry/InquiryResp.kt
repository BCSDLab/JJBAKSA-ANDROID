package com.jjbaksa.data.model.inquiry

import com.google.gson.annotations.SerializedName

data class InquiryResp(
    @SerializedName("content")
    val content: List<InquiryContentResp>? = emptyList(),
)
