package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.FollowerDataSource
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowerListResp
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

    override suspend fun followRequest(userAccount: String?): Response<FollowRequestResp> {
        return authApi.followRequest(userAccount)
    }

    override suspend fun followRequestAccept(userAccount: String): Response<FollowResp> {
        return authApi.followRequestAccept(userAccount)
    }

    override suspend fun followRequestCancle(userAccount: String): Response<Unit> {
        return authApi.followRequestCancle(userAccount)
    }

    override suspend fun followRequestReject(userAccount: String): Response<Unit> {
        return authApi.followRequestReject(userAccount)
    }
}