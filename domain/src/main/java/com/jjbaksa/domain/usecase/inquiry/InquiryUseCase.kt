package com.jjbaksa.domain.usecase.inquiry

import com.jjbaksa.domain.repository.InquiryRepository
import com.jjbaksa.domain.model.inquiry.Inquiry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInquiryUseCase @Inject constructor(
    private val inquiryRepository: InquiryRepository
) {
    suspend operator fun invoke(
        idCursor: Int?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>> {
        return inquiryRepository.getInquiry(idCursor, dateCursor, size)
    }
    suspend fun 
}
