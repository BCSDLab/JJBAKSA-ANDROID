package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.inquiry.InquiryContentResp
import com.jjbaksa.data.model.inquiry.InquiryResp
import okhttp3.MultipartBody
import retrofit2.Response

interface InquiryDataSource {
    suspend fun getInquiry(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Response<InquiryResp>
    suspend fun getInquiryMe(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Response<InquiryResp>
    suspend fun postInquiry(
        title: String,
        content: String,
        isSecret: Boolean,
        inquiryImages: List<MultipartBody.Part>
    ): Response<InquiryContentResp>
}
