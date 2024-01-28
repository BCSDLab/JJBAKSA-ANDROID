package com.jjbaksa.domain.usecase.follower

import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.FollowRequestCheck
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

    suspend fun followRequestAccept(userAccount: String): Flow<Result<Follow>> {
        return followerRepository.followRequestAccept(userAccount)
    }
    suspend fun followRequestReject(userAccount: String): Flow<Result<Unit>> {
        return followerRepository.followRequestReject(userAccount)
    }

    suspend fun followRequestCheck(page: Int? ,pageSize: Int?): Flow<Result<FollowRequestCheck>> {
        return followerRepository.followRequestCheck(page, pageSize)
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
