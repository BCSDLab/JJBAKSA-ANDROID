package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.Followers
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.ReviewShop
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
    suspend fun getBeRequestedFollowers(page: Int?, pageSize: Int?): Flow<Result<Followers>>
    suspend fun getRequestedFollowers(page: Int?, pageSize: Int?): Flow<Result<Followers>>
    suspend fun followRequestCancel(request_id: Long): Flow<Result<Unit>>
    suspend fun getRecentlyActiveFollowers(pageSize: Int?, cursor: Long?): Flow<Result<FollowerList>>
    suspend fun getFollowerReviewCount(id: Long): Flow<Result<Int>>
    suspend fun getReviewedShops(id: Long): Flow<Result<ReviewShop>>
    suspend fun getShopReview(id: Long, placeId: String): Flow<Result<FollowerReviewShops>>
}
