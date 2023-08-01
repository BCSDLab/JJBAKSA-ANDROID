package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.PostDataSource
import com.jjbaksa.data.model.post.PostResp
import retrofit2.Response
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
): PostDataSource {
    override suspend fun getPost(
        idCursor: String,
        dateCursor: String,
        size: Int
    ): Response<PostResp> {
        return  noAuthApi.getPost(idCursor, dateCursor, size)
    }
}
