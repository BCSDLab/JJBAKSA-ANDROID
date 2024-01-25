package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.follower.FollowerResp
import retrofit2.Response

interface FollowerDataSource {
    suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Response<FollowerResp>

}