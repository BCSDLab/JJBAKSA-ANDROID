package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.model.inquiry.InquiryContent
import kotlinx.coroutines.flow.Flow

interface InquiryRepository {
    suspend fun getInquiry(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>>
    suspend fun getInquiryMe(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>>
    suspend fun setInquiry(
        title: String,
        content: String,
        isSecret: Boolean,
        inquiryImages: List<String>
    ): Flow<Result<InquiryContent>>
}
