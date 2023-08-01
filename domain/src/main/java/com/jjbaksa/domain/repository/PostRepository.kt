package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.post.PostData
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPost(
        idCursor: String, dateCursor: String, size: Int
    ): Flow<Result<PostData>>
}