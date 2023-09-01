package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.review.ReviewShop
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getReviewShop(
        cursor: Int?,
        size: Int
    ): Flow<Result<ReviewShop>>
}
