package com.jjbaksa.domain.usecase.post

import com.jjbaksa.domain.repository.PostRepository
import com.jjbaksa.domain.resp.post.PostData
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
}
