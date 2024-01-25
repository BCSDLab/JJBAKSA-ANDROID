package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.FollowerRemoteDataSource
import com.jjbaksa.data.mapper.follower.toFollowRequest
import com.jjbaksa.data.mapper.follower.toFollower
import com.jjbaksa.data.mapper.inquiry.toInquiry
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.Follower
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.repository.FollowerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FollowerRepositoryImpl @Inject constructor(
    private val followerRemoteDataSource: FollowerRemoteDataSource
) : FollowerRepository {

    override suspend fun getFollower(
        cursor: String?,
        pageSize: Int?
    ): Flow<Result<Follower>> {
        return apiCall(
            call = { followerRemoteDataSource.getFollower(cursor, pageSize) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollower() ?: Follower()
                } else {
                    Follower()
                }
            }

        )
    }

    override suspend fun followRequest(userAccount: String?): Flow<Result<FollowRequest>> {
        return apiCall(
            call = { followerRemoteDataSource.followRequest(userAccount) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollowRequest() ?: FollowRequest()
                } else {
                    FollowRequest()
                }
            }
        )
    }
}