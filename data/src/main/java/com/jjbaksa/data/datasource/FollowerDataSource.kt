package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowReq
import com.jjbaksa.data.model.follower.FollowRequestCheckResp
import com.jjbaksa.data.model.follower.FollowerListResp
import retrofit2.Response

interface FollowerDataSource {
    suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Response<FollowerListResp>

    suspend fun followRequest(req: FollowReq): Response<FollowRequestResp>
    suspend fun followerDelete(req: FollowReq): Response<Unit>
    suspend fun followRequestAccept(userId : Long): Response<FollowResp>
    suspend fun followRequestReject(userId : Long): Response<Unit>
    suspend fun followRequestCheck(page: Int?, pageSize: Int?): Response<FollowRequestCheckResp>
    suspend fun followRequestSend(page: Int?, pageSize: Int?): Response<FollowRequestCheckResp>
}