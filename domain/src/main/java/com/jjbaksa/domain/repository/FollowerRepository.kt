package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.FollowRequestCheck
import com.jjbaksa.domain.model.follower.FollowerList
import kotlinx.coroutines.flow.Flow

interface FollowerRepository {
    suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Flow<Result<FollowerList>>

    suspend fun followRequest(userAccount: String?): Flow<Result<FollowRequest>>
    suspend fun followerDelete(userAccount: String): Flow<Result<Unit>>
    suspend fun followRequestAccept(userId: Long): Flow<Result<Follow>>

    suspend fun followRequestReject(userId: Long): Flow<Result<Unit>>
    suspend fun followRequestCheck(page: Int?, pageSize: Int?): Flow<Result<FollowRequestCheck>>

}
