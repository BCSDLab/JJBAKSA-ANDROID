package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowerListResp
import retrofit2.Response

interface FollowerDataSource {
    suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Response<FollowerListResp>

    suspend fun followRequest(userAccount: String?): Response<FollowRequestResp>
    suspend fun followRequestAccept(userAccount: String): Response<FollowResp>
    suspend fun followRequestCancle(userAccount: String): Response<Unit>
    suspend fun followRequestReject(userAccount: String): Response<Unit>
}