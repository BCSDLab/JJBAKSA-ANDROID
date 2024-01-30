package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.FollowerDataSource
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowReq
import com.jjbaksa.data.model.follower.FollowersListResp
import com.jjbaksa.data.model.follower.FollowerListResp
import com.jjbaksa.data.model.follower.FollowerReviewShopsResp
import com.jjbaksa.data.model.review.ReviewCountResp
import com.jjbaksa.data.model.review.ReviewShopResp
import retrofit2.Response
import javax.inject.Inject

class FollowerRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : FollowerDataSource {
    override suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Response<FollowerListResp> {
        return authApi.getFollower(cursor, pageSize)
    }

    override suspend fun followRequest(req: FollowReq): Response<FollowRequestResp> {
        return authApi.followRequest(req)
    }

    override suspend fun followerDelete(req: FollowReq): Response<Unit> {
        return authApi.followerDelete(req)
    }

    override suspend fun followRequestAccept(userId :Long): Response<FollowResp> {
        return authApi.followRequestAccept(userId)
    }

    override suspend fun followRequestCheck(page: Int?, pageSize: Int?): Response<FollowersListResp> {
        return authApi.followRequestReceived(page, pageSize)
    }

    override suspend fun getRecentlyActiveFollowers(pageSize: Int?, cursor: Long?): Response<FollowerListResp> {
        return authApi.getRecentlyActiveFollowers(pageSize, cursor)
    }

    override suspend fun getFollowerReviewCount(id: Long): Response<ReviewCountResp> {
        return authApi.getFollowerReviewCount(id)
    }

    override suspend fun getReviewedShops(id: Long): Response<ReviewShopResp> {
        return authApi.getReviewedShops(id)
    }

    override suspend fun getShopReview(id: Long, placeId: String): Response<FollowerReviewShopsResp> {
        return authApi.getShopReview(id, placeId)
    }

    override suspend fun followRequestReject(userId: Long): Response<Unit> {
        return authApi.followRequestReject(userId)
    }

    override suspend fun followRequestReceived(page: Int?, pageSize: Int?): Response<FollowersListResp> {
        return authApi.followRequestReceived(page, pageSize)
    }

    override suspend fun followRequestSend(page: Int?, pageSize: Int?): Response<FollowersListResp> {
        return authApi.followRequestSend(page, pageSize)
    }
}