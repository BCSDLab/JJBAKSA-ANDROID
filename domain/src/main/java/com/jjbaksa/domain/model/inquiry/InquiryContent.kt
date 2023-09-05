package com.jjbaksa.domain.model.inquiry

data class InquiryContent(
    val id: Long = 0,
    val answer: String = "",
    val content: String = "",
    val createdAt: String = "",
    val createdBy: String = "",
    val inquiryImages: List<InquiryImages> = emptyList(),
    val isSecreted: Int = 0,
    val title: String = ""
)
