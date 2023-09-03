package com.jjbaksa.domain.usecase.inquiry

import com.jjbaksa.domain.repository.InquiryRepository
import com.jjbaksa.domain.model.inquiry.InquiryData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInquiryUseCase @Inject constructor(
    private val inquiryRepository: InquiryRepository
) {
    suspend operator fun invoke(
        idCursor: String,
        dateCursor: String,
        size: Int
    ): Flow<Result<InquiryData>> {
        return inquiryRepository.getInquiry(idCursor, dateCursor, size)
    }
}
