package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.ReviewDataSource
import com.jjbaksa.data.model.follower.FollowerReviewShopsResp
import com.jjbaksa.data.model.review.ReviewShopLastDateResp
import com.jjbaksa.data.model.review.MyReviewShopsResp
import com.jjbaksa.data.model.review.ReviewCountResp
import com.jjbaksa.data.model.review.ReviewShopDetailResp
import com.jjbaksa.data.model.review.ReviewShopResp
import okhttp3.MultipartBody
import retrofit2.Response

class ReviewRemoteDataSource(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : ReviewDataSource {
    override suspend fun getReviewShop(cursor: Int?, size: Int): Response<ReviewShopResp> {
        return authApi.getMyReviewShops(cursor, size)
    }

    override suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Response<MyReviewShopsResp> {
        return authApi.getMyReviewShops(
            placeId,
            idCursor,
            dateCursor,
            rateCursor,
            size,
            direction,
            sort
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
    ): Response<FollowerReviewShopsResp> {
        return authApi.getFollowerReviewShops(
            placeId,
            idCursor,
            dateCursor,
            rateCursor,
            size,
            direction,
            sort
        )
    }

    override suspend fun getMyReviewShopLastDate(placeId: String): Response<ReviewShopLastDateResp> {
        return authApi.getMyReviewShopLastDate(placeId)
    }

    override suspend fun getFollowerReviewShopsLastDate(placeId: String): Response<ReviewShopLastDateResp> {
        return authApi.getFollowerReviewShopsLastDate(placeId)
    }

    override suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<MultipartBody.Part>
    ): Response<ReviewShopDetailResp> {
        return authApi.postReview(placeId, content, rate, reviewImages)
    }

    override suspend fun getFollowersShopReviewCount(placeId: String): Response<ReviewCountResp> {
        return authApi.getFollowersShopReviewCount(placeId)
    }
}
