package com.jjbaksa.domain.usecase.review

import com.jjbaksa.domain.repository.ReviewRepository
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.ReviewShopLastDate
import com.jjbaksa.domain.model.review.MyReviewShops
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.model.review.ReviewShopDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend fun getReviewShop(cursor: Int?, size: Int): Flow<Result<ReviewShop>> {
        return reviewRepository.getReviewShop(cursor, size)
    }
    suspend fun getMyReview(
        placeId: String,
        idCursor: Int? = null,
        dateCursor: String? = null,
        rateCursor: Int? = null,
        size: Int? = null, // 1~10
        direction: String? = null, // asc / desc
        sort: String? = null // createdAt / rate
    ): Flow<Result<MyReviewShops>> {
        return reviewRepository.getMyReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
    }

    suspend fun getFollowerReviewShops(
        placeId: String,
        idCursor: Int? = null,
        dateCursor: String? = null,
        rateCursor: Int? = null,
        size: Int? = null,
        direction: String? = null,
        sort: String? = null
    ): Flow<Result<FollowerReviewShops>> {
        return reviewRepository.getFollowerReviewShops(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
    }
    suspend fun getMyReviewShopLastDate(placeId: String): Flow<Result<ReviewShopLastDate>> {
        return reviewRepository.getMyReviewShopLastDate(placeId)
    }

    suspend fun getFollowerReviewShopLastDate(placeId: String): Flow<Result<ReviewShopLastDate>> {
        return reviewRepository.getFollowerReviewShopLastDate(placeId)
    }

    suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<String>
    ): Flow<Result<ReviewShopDetail>> {
        return reviewRepository.setReview(placeId, content, rate, reviewImages)
    }


    suspend fun getFollowersShopReviewCount(placeId: String): Flow<Result<Int>> {
        return reviewRepository.getFollowersShopReviewCount(placeId)
    }
}
