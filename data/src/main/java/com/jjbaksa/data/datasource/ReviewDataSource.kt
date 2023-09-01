package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.review.ReviewShopResp
import retrofit2.Response

interface ReviewDataSource {
    suspend fun getReviewShop(
        cursor: Int?,
        size: Int
    ): Response<ReviewShopResp>
}
