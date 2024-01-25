package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowerResp
import retrofit2.Response

interface FollowerDataSource {
    suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Response<FollowerResp>

    suspend fun followRequest(userAccount: String?): Response<FollowRequestResp>

}