package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.InquiryRemoteDataSource
import com.jjbaksa.data.mapper.inquiry.toInquiry
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.InquiryRepository
import com.jjbaksa.domain.model.inquiry.Inquiry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InquiryRepositoryImpl @Inject constructor(
    private val inquiryRemoteDataSource: InquiryRemoteDataSource
) : InquiryRepository {
    override suspend fun getInquiry(
        idCursor: Int?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>> {
        return apiCall(
            call = { inquiryRemoteDataSource.getInquiry(idCursor, dateCursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toInquiry() ?: Inquiry()
                } else {
                    Inquiry()
                }
            }
        )
    }
}
