package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.inquiry.InquiryData
import kotlinx.coroutines.flow.Flow

interface InquiryRepository {
    suspend fun getInquiry(
        idCursor: String,
        dateCursor: String,
        size: Int
    ): Flow<Result<InquiryData>>
}