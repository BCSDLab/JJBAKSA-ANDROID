package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.FollowerRemoteDataSource
import com.jjbaksa.data.mapper.follower.toFollow
import com.jjbaksa.data.mapper.follower.toFollowRequest
import com.jjbaksa.data.mapper.follower.tofollowRequestRecived
import com.jjbaksa.data.mapper.follower.toFollower
import com.jjbaksa.data.mapper.review.toFollowerShopReview
import com.jjbaksa.data.mapper.review.toReviewShop
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.follower.FollowReq
import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.Followers
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.repository.FollowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FollowerRepositoryImpl @Inject constructor(
    private val followerRemoteDataSource: FollowerRemoteDataSource
) : FollowerRepository {

    override suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Flow<Result<FollowerList>> {
        return apiCall(
            call = { followerRemoteDataSource.getFollower(cursor, pageSize) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollower() ?: FollowerList()
                } else {
                    FollowerList()
                }
            }

        )
    }

    override suspend fun followRequest(userAccount: String?): Flow<Result<FollowRequest>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequest(FollowReq(userAccount)) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollowRequest() ?: FollowRequest()
                } else {
                    FollowRequest()
                }
            }
        )
    }



    override suspend fun followerDelete(userAccount: String): Flow<Result<Unit>> {
        return apiCall(
            call = { followerRemoteDataSource.followerDelete(FollowReq(userAccount)) },
            mapper = {
                Unit
            }
        )
    }

    override suspend fun followRequestAccept(userId : Long): Flow<Result<Follow>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequestAccept(userId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollow() ?: Follow()
                } else {
                    Follow()
                }
            }
        )
    }

    override suspend fun followRequestReject(userId : Long): Flow<Result<Unit>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequestReject(userId) },
            mapper = {
                Unit
            }
        )
    }

    override suspend fun followRequestRecived(
        page: Int?,
        pageSize: Int?
    ): Flow<Result<Followers>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequestReceived(page, pageSize) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.tofollowRequestRecived() ?: Followers()

                } else {
                    Followers()
                }
            }
        )
    }

    override suspend fun followRequestSend(
        page: Int?,
        pageSize: Int?
    ): Flow<Result<Followers>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequestSend(page, pageSize) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.tofollowRequestRecived() ?: Followers()

                } else {
                    Followers()
                }
            }
        )
    }

    override suspend fun getRecentlyActiveFollowers(pageSize: Int? ,cursor: Long?): Flow<Result<FollowerList>> {
        return apiCall(
            call = { followerRemoteDataSource.getRecentlyActiveFollowers(pageSize, cursor) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollower() ?: FollowerList()
                } else {
                    FollowerList()
                }
            }
        )
    }

    override suspend fun getFollowerReviewCount(id: Long): Flow<Result<Int>> {
        return apiCall(
            call = { followerRemoteDataSource.getFollowerReviewCount(id) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.count ?: 0
                } else {
                    0
                }
            }
        )
    }

    override suspend fun getReviewedShops(id: Long): Flow<Result<ReviewShop>> {
        return apiCall(
            call = { followerRemoteDataSource.getReviewedShops(id) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toReviewShop() ?: ReviewShop()
                } else {
                    ReviewShop()
                }
            }
        )
    }

    override suspend fun getShopReview(id: Long, placeId: String): Flow<Result<FollowerReviewShops>> {
        return apiCall(
            call = { followerRemoteDataSource.getShopReview(id, placeId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollowerShopReview() ?: FollowerReviewShops()
                } else {
                    FollowerReviewShops()
                }
            }
        )
    }
}