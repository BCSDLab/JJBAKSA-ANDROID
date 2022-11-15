package com.jjbaksa.data.datasource.local

import com.jjbaksa.data.datasource.ShopDataSource
import com.jjbaksa.data.model.shop.TrendingResp
import retrofit2.Response

class ShopLocalDataSource: ShopDataSource {
    override suspend fun getTrendings(): Response<TrendingResp> {
        TODO("Not yet implemented")
    }
}