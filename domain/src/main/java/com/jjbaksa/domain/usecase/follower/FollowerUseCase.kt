package com.jjbaksa.domain.usecase.follower

import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
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

    suspend fun followRequestAccept(userAccount: String): Flow<Result<Follow>> {
        return followerRepository.followRequestAccept(userAccount)
    }
    suspend fun followerDelete(userAccount: String): Flow<Result<Unit>> {
        return followerRepository.followerDelete(userAccount)
    }

    suspend fun followRequestReject(userAccount: String): Flow<Result<Unit>> {
        return followerRepository.followRequestReject(userAccount)
    }

}
