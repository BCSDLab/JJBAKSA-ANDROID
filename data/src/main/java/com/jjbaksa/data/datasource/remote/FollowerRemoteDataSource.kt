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
import com.jjbaksa.domain.model.follower.FollowRequestCheck
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

    override suspend fun followRequestReject(userId: Long): Response<Unit> {
        return authApi.followRequestReject(userId)
    }

    override suspend fun followRequestCheck(page: Int?, pageSize: Int?): Response<FollowRequestCheckResp> {
        return authApi.followRequestCheck(page, pageSize)
    }
}