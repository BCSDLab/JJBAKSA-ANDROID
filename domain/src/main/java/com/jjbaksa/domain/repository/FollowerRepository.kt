package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.follower.Follower
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.model.inquiry.InquiryContent
import kotlinx.coroutines.flow.Flow

interface FollowerRepository {
    suspend fun getFollower(
        cursor: String,
        pageSize: Int
    ): Flow<Result<Follower>>

}
