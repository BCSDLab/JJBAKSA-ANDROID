package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.FollowerRemoteDataSource
import com.jjbaksa.data.mapper.follower.toFollow
import com.jjbaksa.data.mapper.follower.toFollowRequest
import com.jjbaksa.data.mapper.follower.toFollowRequestCheck
import com.jjbaksa.data.mapper.follower.toFollower
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.follower.FollowReq
import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.FollowRequestCheck
import com.jjbaksa.domain.model.follower.FollowerList
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

    override suspend fun followRequestAccept(userAccount: String): Flow<Result<Follow>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequestAccept(userAccount) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollow() ?: Follow()
                } else {
                    Follow()
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


    override suspend fun followRequestReject(userAccount: String): Flow<Result<Unit>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequestReject(userAccount) },
            mapper = {
                Unit
            }
        )
    }

    override suspend fun followRequestCheck(
        page: Int?,
        pageSize: Int?
    ): Flow<Result<FollowRequestCheck>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequestCheck(page, pageSize) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollowRequestCheck() ?: FollowRequestCheck()
                } else {
                    FollowRequestCheck()
                }
            }
        )
    }
}