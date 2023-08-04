package com.jjbaksa.domain.resp.inquiry

data class InquiryData(
    val content: List<InquiryContent> = emptyList(),
    val size: Int = 0,
)
