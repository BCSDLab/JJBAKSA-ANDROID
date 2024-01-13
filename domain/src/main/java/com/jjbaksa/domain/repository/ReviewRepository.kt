package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.ReviewShopLastDate
import com.jjbaksa.domain.model.review.MyReviewShops
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.model.review.ReviewShopDetail
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getReviewShop(
        cursor: Int?,
        size: Int
    ): Flow<Result<ReviewShop>>

    suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<MyReviewShops>>

    suspend fun getFollowerReviewShops(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<FollowerReviewShops>>

    suspend fun getMyReviewShopLastDate(placeId: String): Flow<Result<ReviewShopLastDate>>
    suspend fun getFollowerReviewShopLastDate(placeId: String): Flow<Result<ReviewShopLastDate>>
    suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<String>
    ): Flow<Result<ReviewShopDetail>>
    suspend fun getFollowersShopReviewCount(placeId: String): Flow<Result<Int>>
}
