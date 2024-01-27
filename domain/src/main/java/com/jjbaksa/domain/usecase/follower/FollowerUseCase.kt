package com.jjbaksa.domain.usecase.follower

import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.FollowRequestCheck
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.repository.FollowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowerUseCase @Inject constructor(
    private val followerRepository: FollowerRepository
) {
    suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Flow<Result<FollowerList>> {
        return followerRepository.getFollower(cursor, pageSize)
    }

    suspend fun followRequest(userAccount: String?): Flow<Result<FollowRequest>> {
        return followerRepository.followRequest(userAccount)
    }

    suspend fun followerDelete(userAccount: String): Flow<Result<Unit>> {
        return followerRepository.followerDelete(userAccount)
    }

    suspend fun followRequestAccept(userId: Long): Flow<Result<Follow>> {
        return followerRepository.followRequestAccept(userId)
    }
    suspend fun followRequestReject(userId: Long): Flow<Result<Unit>> {
        return followerRepository.followRequestReject(userId)
    }

    suspend fun followRequestCheck(page: Int? ,pageSize: Int?): Flow<Result<FollowRequestCheck>> {
        return followerRepository.followRequestCheck(page, pageSize)
    }


}
