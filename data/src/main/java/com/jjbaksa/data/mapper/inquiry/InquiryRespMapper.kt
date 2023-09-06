package com.jjbaksa.data.mapper.inquiry

import com.jjbaksa.data.model.inquiry.InquiryResp
import com.jjbaksa.data.model.inquiry.InquiryContentResp
import com.jjbaksa.data.model.inquiry.InquiryImagesResp
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.model.inquiry.InquiryContent
import com.jjbaksa.domain.model.inquiry.InquiryImages

fun InquiryResp.toInquiry() = Inquiry(
    content = content?.map { it.toInquiryContent() }.orEmpty()
)

fun InquiryContentResp.toInquiryContent() = InquiryContent(
    id = id ?: 0,
    answer = answer ?: "",
    content = content ?: "",
    createdAt = createdAt ?: "",
    createdBy = createdBy ?: "",
    inquiryImages = inquiryImages?.map { it.toInquiryImages() }.orEmpty(),
    isSecreted = isSecreted ?: 0,
    title = title ?: ""
)

fun InquiryImagesResp.toInquiryImages() = InquiryImages(
    imageUrl = imageUrl ?: "",
    originalName = originalName ?: "",
    path = path ?: ""
)
