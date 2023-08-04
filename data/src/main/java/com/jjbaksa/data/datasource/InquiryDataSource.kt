package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.inquiry.InquiryResp
import retrofit2.Response

interface InquiryDataSource {
    suspend fun getInquiry(
        idCursor: String?,
        dateCursor: String?,
        size: Int
    ): Response<InquiryResp>
}
