package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.ReviewDataSource
import com.jjbaksa.data.model.review.ReviewShopResp
import retrofit2.Response

class ReviewRemoteDataSource(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : ReviewDataSource {
    override suspend fun getReviewShop(cursor: Int?, size: Int): Response<ReviewShopResp> {
        return authApi.getReviewShop(cursor, size)
    }
}
