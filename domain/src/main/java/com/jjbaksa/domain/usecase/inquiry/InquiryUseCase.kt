package com.jjbaksa.domain.usecase.inquiry

import com.jjbaksa.domain.repository.InquiryRepository
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.model.inquiry.InquiryContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InquiryUseCase @Inject constructor(
    private val inquiryRepository: InquiryRepository
) {
    suspend fun getInquiry(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>> {
        return inquiryRepository.getInquiry(idCursor, dateCursor, size)
    }
    suspend fun getInquiryMe(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>> {
        return inquiryRepository.getInquiryMe(idCursor, dateCursor, size)
    }
    suspend fun setInquiry(
        title: String,
        content: String,
        isSecret: Boolean,
        inquiryImages: List<String>
    ): Flow<Result<InquiryContent>> {
        return inquiryRepository.setInquiry(title, content, isSecret, inquiryImages)
    }
    suspend fun getInquirySearch(
        searchWord: String,
        dateCursor: String?,
        idCursor: Long?,
        size: Int
    ): Flow<Result<Inquiry>> {
        return inquiryRepository.getInquirySearch(searchWord, dateCursor, idCursor, size)
    }
    suspend fun getInquirySearchMe(
        searchWord: String,
        dateCursor: String?,
        idCursor: Long?,
        size: Int
    ): Flow<Result<Inquiry>> {
        return inquiryRepository.getInquirySearchMe(searchWord, dateCursor, idCursor, size)
    }
}
