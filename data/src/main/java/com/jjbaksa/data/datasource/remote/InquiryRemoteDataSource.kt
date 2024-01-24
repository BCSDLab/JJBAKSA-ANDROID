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
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Response<InquiryResp> {
        return authApi.getInquiry(idCursor, dateCursor, size)
    }

    override suspend fun getInquiryMe(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Response<InquiryResp> {
        return authApi.getInquiryMe(idCursor, dateCursor, size)
    }

    override suspend fun postInquiry(
        title: String,
        content: String,
        isSecret: Boolean,
        inquiryImages: List<MultipartBody.Part>
    ): Response<InquiryContentResp> {
        return authApi.postInquiry(title, content, isSecret, inquiryImages)
    }

    override suspend fun getInquirySearch(
        searchWord: String,
        dateCursor: String?,
        idCursor: Long?,
        size: Int
    ): Response<InquiryResp> {
        return authApi.getInquirySearch(searchWord, dateCursor, idCursor, size)
    }

    override suspend fun getInquirySearchMe(
        searchWord: String,
        dateCursor: String?,
        idCursor: Long?,
        size: Int
    ): Response<InquiryResp> {
        return authApi.getInquirySearchMe(searchWord, dateCursor, idCursor, size)
    }
}
