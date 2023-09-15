package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.InquiryDataSource
import com.jjbaksa.data.model.inquiry.InquiryContentResp
import com.jjbaksa.data.model.inquiry.InquiryResp
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class InquiryRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : InquiryDataSource {
    override suspend fun getInquiry(
        idCursor: Int?,
        dateCursor: String?,
        size: Int
    ): Response<InquiryResp> {
        return authApi.getInquiry(idCursor, dateCursor, size)
    }

    override suspend fun postInquiry(
        title: String,
        content: String,
        isSecret: Boolean,
        inquiryImages: List<MultipartBody.Part>
    ): Response<InquiryContentResp> {
        return authApi.postInquiry(title, content, isSecret, inquiryImages)
    }
}
