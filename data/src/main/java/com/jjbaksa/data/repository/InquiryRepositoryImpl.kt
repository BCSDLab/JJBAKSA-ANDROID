package com.jjbaksa.data.repository

import android.net.Uri
import com.jjbaksa.data.datasource.remote.InquiryRemoteDataSource
import com.jjbaksa.data.mapper.FormDataUtil
import com.jjbaksa.data.mapper.inquiry.toInquiry
import com.jjbaksa.data.mapper.inquiry.toInquiryContent
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.InquiryRepository
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.model.inquiry.InquiryContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InquiryRepositoryImpl @Inject constructor(
    private val inquiryRemoteDataSource: InquiryRemoteDataSource
) : InquiryRepository {
    override suspend fun getInquiry(
        idCursor: Long?,
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

    override suspend fun getInquiryMe(
        idCursor: Long?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Inquiry>> {
        return apiCall(
            call = { inquiryRemoteDataSource.getInquiryMe(idCursor, dateCursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toInquiry() ?: Inquiry()
                } else {
                    Inquiry()
                }
            }
        )
    }

    override suspend fun setInquiry(
        title: String,
        content: String,
        isSecret: Boolean,
        inquiryImages: List<String>
    ): Flow<Result<InquiryContent>> {
        return apiCall(
            call = {
                var filesBody = inquiryImages.map {
                    FormDataUtil.getImageBody("inquiryImages", Uri.parse(it))
                }
                if (filesBody.isNullOrEmpty()) {
                    filesBody = listOf(FormDataUtil.getEmptyBody())
                }
                inquiryRemoteDataSource.postInquiry(title, content, isSecret, filesBody)
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toInquiryContent() ?: InquiryContent()
                } else {
                    InquiryContent()
                }
            }
        )
    }
}
