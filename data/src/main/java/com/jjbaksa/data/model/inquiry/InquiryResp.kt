package com.jjbaksa.data.model.inquiry

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.inquiry.dto.InquiryContentDTO
import com.jjbaksa.data.model.inquiry.dto.InquiryPageableDTO

data class InquiryResp(
    @SerializedName("content")
    val content: List<InquiryContentDTO>?,
    @SerializedName("empty")
    val empty: Boolean?,
    @SerializedName("first")
    val first: Boolean?,
    @SerializedName("last")
    val last: Boolean?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("numberOfElements")
    val numberOfElements: Int?,
    @SerializedName("pageable")
    val pageable: List<InquiryPageableDTO>?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("totalElements")
    val totalElements: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?
)
