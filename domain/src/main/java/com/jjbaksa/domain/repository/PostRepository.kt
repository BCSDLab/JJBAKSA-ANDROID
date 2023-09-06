package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.post.Post
import com.jjbaksa.domain.model.post.PostDetail
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPost(
        idCursor: Int?,
        dateCursor: String?,
        size: Int
    ): Flow<Result<Post>>

    suspend fun getPostDetail(postId: Int): Flow<Result<PostDetail>>
}
