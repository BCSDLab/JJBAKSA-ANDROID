package com.jjbaksa.domain.usecase.review

import com.jjbaksa.domain.repository.ReviewRepository
import com.jjbaksa.domain.resp.review.ReviewShop
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend fun getReviewShop(cursor: Int?, size: Int): Flow<Result<ReviewShop>> {
        return reviewRepository.getReviewShop(cursor, size)
    }
}
