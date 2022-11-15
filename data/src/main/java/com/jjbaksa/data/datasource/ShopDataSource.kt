package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.shop.TrendingResp
import retrofit2.Response

interface ShopDataSource {
    suspend fun getTrendings(): Response<TrendingResp>
}