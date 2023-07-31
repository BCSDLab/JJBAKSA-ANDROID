package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.InquiryRemoteDataSource
import com.jjbaksa.data.mapper.toInquiryData
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.InquiryRepository
import com.jjbaksa.domain.resp.inquiry.InquiryData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InquiryRepositoryImpl @Inject constructor(
    private val inquiryRemoteDataSource: InquiryRemoteDataSource
) : InquiryRepository {
    override suspend fun getInquiry(
        idCursor: String,
        dateCursor: String,
        size: Int
    ): Flow<Result<InquiryData>> {
        return apiCall(
            call = { inquiryRemoteDataSource.getInquiry(idCursor, dateCursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toInquiryData()
                } else {
                    InquiryData()
                }
            }
        )
    }
}
