package com.jjbaksa.domain.resp.inquiry

data class InquiryContent(
    val id: Int,
    val answer: String = "",
    val content: String = "",
    val createdAt: String = "",
    val createdBy : String = "",
    val inquiryImages: List<InquiryImages> = emptyList(),
    val isSecreted: Int = 0,
    val title: String = ""
)
