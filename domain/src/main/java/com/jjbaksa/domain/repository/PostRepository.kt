package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.post.PostData
import com.jjbaksa.domain.model.post.PostDetail
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPost(
        idCursor: String,
        dateCursor: String,
        size: Int
    ): Flow<Result<PostData>>

    suspend fun getPostDetail(postId: Int): Flow<Result<PostDetail>>
}
