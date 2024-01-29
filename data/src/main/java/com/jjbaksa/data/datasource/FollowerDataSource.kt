package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowReq
import com.jjbaksa.data.model.follower.FollowerslistResp
import com.jjbaksa.data.model.follower.FollowerListResp
import com.jjbaksa.data.model.follower.FollowerReviewShopsResp
import com.jjbaksa.data.model.review.ReviewCountResp
import com.jjbaksa.data.model.review.ReviewShopResp
import retrofit2.Response

interface FollowerDataSource {
    suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Response<FollowerListResp>

    suspend fun followRequest(req: FollowReq): Response<FollowRequestResp>
    suspend fun followerDelete(req: FollowReq): Response<Unit>
    suspend fun followRequestCheck(page: Int?, pageSize: Int?): Response<FollowerslistResp>
    suspend fun getFollowerReviewCount(id: Long): Response<ReviewCountResp>
    suspend fun getReviewedShops(id: Long): Response<ReviewShopResp>
    suspend fun getShopReview(id: Long, placeId: String): Response<FollowerReviewShopsResp>
    suspend fun followRequestAccept(userId : Long): Response<FollowResp>
    suspend fun followRequestReject(userId : Long): Response<Unit>
    suspend fun followRequestReceived(page: Int?, pageSize: Int?): Response<FollowerslistResp>
    suspend fun followRequestSend(page: Int?, pageSize: Int?): Response<FollowerslistResp>
    suspend fun getRecentlyActiveFollowers(pageSize: Int?, cursor: Long?): Response<FollowerslistResp>
}