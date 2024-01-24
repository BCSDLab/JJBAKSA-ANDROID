package com.jjbaksa.data.repository

import android.net.Uri
import com.jjbaksa.data.datasource.local.UserLocalDataSource
import com.jjbaksa.data.datasource.remote.ReviewRemoteDataSource
import com.jjbaksa.data.mapper.FormDataUtil
import com.jjbaksa.data.mapper.review.toFollowerShopReview
import com.jjbaksa.data.mapper.review.toReviewShopLastDate
import com.jjbaksa.data.mapper.review.toMyReviewShops
import com.jjbaksa.data.mapper.review.toReviewShop
import com.jjbaksa.data.mapper.review.toReviewShopDetail
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.domain.repository.ReviewRepository
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.ReviewShopLastDate
import com.jjbaksa.domain.model.review.MyReviewShops
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.model.review.ReviewShopDetail
import kotlinx.coroutines.flow.Flow

class ReviewRepositoryImpl(
    private val reviewRemoteDataSource: ReviewRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
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

    override suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<MyReviewShops>> {
        return apiCall(
            call = {
                reviewRemoteDataSource.getMyReview(
                    placeId,
                    idCursor,
                    dateCursor,
                    rateCursor,
                    size,
                    direction,
                    sort
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toMyReviewShops() ?: MyReviewShops()
                } else {
                    MyReviewShops()
                }
            }
        )
    }

    override suspend fun getFollowerReviewShops(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<FollowerReviewShops>> {
        return apiCall(
            call = {
                reviewRemoteDataSource.getFollowerReviewShops(
                    placeId, idCursor, dateCursor, rateCursor, size, direction, sort
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toFollowerShopReview() ?: FollowerReviewShops()
                } else {
                    FollowerReviewShops()
                }
            }
        )
    }

    override suspend fun getMyReviewShopLastDate(placeId: String): Flow<Result<ReviewShopLastDate>> {
        return apiCall(
            call = { reviewRemoteDataSource.getMyReviewShopLastDate(placeId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toReviewShopLastDate() ?: ReviewShopLastDate()
                } else {
                    ReviewShopLastDate()
                }
            }
        )
    }

    override suspend fun getFollowerReviewShopLastDate(placeId: String): Flow<Result<ReviewShopLastDate>> {
        return apiCall(
            call = { reviewRemoteDataSource.getFollowerReviewShopsLastDate(placeId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toReviewShopLastDate() ?: ReviewShopLastDate()
                } else {
                    ReviewShopLastDate()
                }
            }
        )
    }

    override suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<String>
    ): Flow<Result<ReviewShopDetail>> {
        return apiCall(
            call = {
                var filesBody = reviewImages.map {
                    FormDataUtil.getImageBody("reviewImages", Uri.parse(it))
                }
                if (filesBody.isNullOrEmpty()) {
                    filesBody = listOf(FormDataUtil.getEmptyBody())
                }
                reviewRemoteDataSource.setReview(placeId, content, rate, filesBody)
            },
            remoteData = {
                if (it.isSuccessful) userLocalDataSource.saveReviews(
                    userLocalDataSource.getReviews().plus(1)
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toReviewShopDetail() ?: ReviewShopDetail()
                } else {
                    ReviewShopDetail()
                }
            }
        )
    }

    override suspend fun getFollowersShopReviewCount(placeId: String): Flow<Result<Int>> {
        return apiCall(
            call = { reviewRemoteDataSource.getFollowersShopReviewCount(placeId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body() ?: 0
                } else {
                    0
                }
            }
        )
    }
}
