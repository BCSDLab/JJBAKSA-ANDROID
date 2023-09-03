package com.jjbaksa.domain.model.inquiry

data class InquiryData(
    val content: List<InquiryContent> = emptyList(),
    val size: Int = 0,
)
