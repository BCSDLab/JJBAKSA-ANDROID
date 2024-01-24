package com.jjbaksa.domain.usecase.follower

import com.jjbaksa.domain.model.follower.Follower
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.model.inquiry.InquiryContent
import com.jjbaksa.domain.repository.FollowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowerUseCase @Inject constructor(
    private val followerRepository: FollowerRepository
) {
    suspend fun getFollower(
        cursor: String,
        pageSize: Int
    ): Flow<Result<Follower>> {
        return followerRepository.getFollower(cursor, pageSize)
    }

}
