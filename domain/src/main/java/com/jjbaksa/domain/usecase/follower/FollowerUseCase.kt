package com.jjbaksa.domain.usecase.follower

import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.Followers
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.ReviewShop
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

    suspend fun getBeRequestedFollowers(page: Int?, pageSize: Int?): Flow<Result<Followers>> {
        return followerRepository.getBeRequestedFollowers(page, pageSize)
    }

    suspend fun getRequestedFollowers(page: Int?, pageSize: Int?): Flow<Result<Followers>> {
        return followerRepository.getRequestedFollowers(page, pageSize)
    }

    suspend fun followRequestCancel(request_id:String): Flow<Result<Unit>> {
        return followerRepository.followRequestCancel(request_id)
    }

    suspend fun getRecentlyActiveFollowers(pageSize: Int?, cursor: Long?): Flow<Result<FollowerList>> {
        return followerRepository.getRecentlyActiveFollowers(pageSize, cursor)
    }

    suspend fun getFollowerReviewCount(id: Long): Flow<Result<Int>> {
        return followerRepository.getFollowerReviewCount(id)
    }

    suspend fun getReviewedShops(id: Long): Flow<Result<ReviewShop>> {
        return followerRepository.getReviewedShops(id)
    }

    suspend fun getShopReview(id: Long, placeId: String): Flow<Result<FollowerReviewShops>> {
        return followerRepository.getShopReview(id, placeId)
    }
}
