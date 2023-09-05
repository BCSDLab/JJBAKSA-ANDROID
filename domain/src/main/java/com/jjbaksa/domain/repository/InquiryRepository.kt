package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.inquiry.Inquiry
import kotlinx.coroutines.flow.Flow

interface InquiryRepository {
    suspend fun getInquiry(
        idCursor: Int?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>>
}
