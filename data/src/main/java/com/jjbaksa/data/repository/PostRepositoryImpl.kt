package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.PostRemoteDataSource
import com.jjbaksa.data.mapper.toPostData
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.PostRepository
import com.jjbaksa.domain.resp.post.PostData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
): PostRepository {
    override suspend fun getPost(
        idCursor: String,
        dateCursor: String,
        size: Int
    ): Flow<Result<PostData>> {
        return apiCall(
            call = { postRemoteDataSource.getPost(idCursor, dateCursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toPostData()
                } else {
                    PostData()
                }
            }
        )
    }
}