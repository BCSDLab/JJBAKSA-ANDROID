package com.jjbaksa.data.datasource.remote

import android.util.Log
import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.FollowerDataSource
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowReq
import com.jjbaksa.data.model.follower.FollowRequestCheckResp
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

    override suspend fun followRequestAccept(userAccount: String): Response<FollowResp> {
        return authApi.followRequestAccept(userAccount)
    }

    override suspend fun followerDelete(req: FollowReq): Response<Unit> {
        return authApi.followerDelete(req)
    }

    override suspend fun followRequestReject(userAccount: String): Response<Unit> {
        return authApi.followRequestReject(userAccount)
    }

    override suspend fun followRequestCheck(page: Int?, pageSize: Int?): Response<FollowRequestCheckResp> {
        return authApi.followRequestCheck(page, pageSize)
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
}