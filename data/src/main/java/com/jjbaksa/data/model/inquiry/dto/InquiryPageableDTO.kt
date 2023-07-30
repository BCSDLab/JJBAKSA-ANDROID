package com.jjbaksa.data.model.inquiry.dto

import com.google.gson.annotations.SerializedName

data class InquiryPageableDTO(
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("pageNumber")
    val pageNumber: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?,
    @SerializedName("paged")
    val paged: Boolean?,
    @SerializedName("sort")
    val sort: List<InquirySortDTO>?,
    @SerializedName("unpaged")
    val unpaged: Boolean?,
)