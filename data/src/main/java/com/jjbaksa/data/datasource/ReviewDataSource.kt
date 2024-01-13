package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.follower.FollowerReviewShopsResp
import com.jjbaksa.data.model.review.ReviewShopLastDateResp
import com.jjbaksa.data.model.review.MyReviewShopsResp
import com.jjbaksa.data.model.review.ReviewShopDetailResp
import com.jjbaksa.data.model.review.ReviewShopResp
import okhttp3.MultipartBody
import retrofit2.Response

interface ReviewDataSource {
    suspend fun getReviewShop(
        cursor: Int?,
        size: Int
    ): Response<ReviewShopResp>

    suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Response<MyReviewShopsResp>

    suspend fun getFollowerReviewShops(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Response<FollowerReviewShopsResp>

    suspend fun getMyReviewShopLastDate(placeId: String): Response<ReviewShopLastDateResp>
    suspend fun getFollowerReviewShopsLastDate(placeId: String): Response<ReviewShopLastDateResp>
    suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<MultipartBody.Part>
    ): Response<ReviewShopDetailResp>
    suspend fun getFollowersShopReviewCount(placeId: String): Response<Int>
}
