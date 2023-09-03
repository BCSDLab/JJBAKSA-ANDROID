package com.jjbaksa.domain.usecase.post

import com.jjbaksa.domain.repository.PostRepository
import com.jjbaksa.domain.model.post.PostData
import com.jjbaksa.domain.model.post.PostDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        idCursor: String,
        dateCursor: String,
        size: Int
    ): Flow<Result<PostData>> {
        return postRepository.getPost(idCursor, dateCursor, size)
    }

    suspend fun postDetail(postId: Int): Flow<Result<PostDetail>> {
        return postRepository.getPostDetail(postId)
    }
}
