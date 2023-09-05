package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.PostRemoteDataSource
import com.jjbaksa.data.mapper.post.toPost
import com.jjbaksa.data.mapper.post.toPostDetail
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.PostRepository
import com.jjbaksa.domain.model.post.Post
import com.jjbaksa.domain.model.post.PostDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {
    override suspend fun getPost(
        idCursor: Int?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Post>> {
        return apiCall(
            call = { postRemoteDataSource.getPost(idCursor, dateCursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toPost() ?: Post()
                } else {
                    Post()
                }
            }
        )
    }

    override suspend fun getPostDetail(postId: Int): Flow<Result<PostDetail>> {
        return apiCall(
            call = { postRemoteDataSource.getPostDetail(postId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toPostDetail() ?: PostDetail()
                } else {
                    PostDetail()
                }
            }
        )
    }
}
