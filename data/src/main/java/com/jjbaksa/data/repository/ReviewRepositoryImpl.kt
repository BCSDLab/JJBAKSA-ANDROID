package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.ReviewRemoteDataSource
import com.jjbaksa.data.mapper.toReviewShop
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.ReviewRepository
import com.jjbaksa.domain.resp.review.ReviewShop
import kotlinx.coroutines.flow.Flow

class ReviewRepositoryImpl(
    private val reviewRemoteDataSource: ReviewRemoteDataSource
) : ReviewRepository {
    override suspend fun getReviewShop(cursor: Int?, size: Int): Flow<Result<ReviewShop>> {
        return apiCall(
            call = { reviewRemoteDataSource.getReviewShop(cursor, size) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toReviewShop() ?: ReviewShop()
                } else {
                    ReviewShop()
                }
            }
        )
    }
}
