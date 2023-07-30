package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.InquiryDataSource
import com.jjbaksa.data.model.inquiry.InquiryResp
import retrofit2.Response
import javax.inject.Inject

class InquiryRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
): InquiryDataSource {
    override suspend fun getInquiry(
        idCursor: String?,
        dateCursor: String?,
        size: Int
    ): Response<InquiryResp> {
        return noAuthApi.getInquiry(idCursor, dateCursor, size)
    }
}