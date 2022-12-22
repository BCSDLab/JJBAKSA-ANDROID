package com.jjbaksa.data.datasource

import com.jjbaksa.data.entity.SearchHistoryEntity
import com.jjbaksa.domain.resp.shop.ShopsResp
import com.jjbaksa.data.model.shop.TrendingResp
import retrofit2.Response

interface ShopDataSource {
    suspend fun getTrendings(): Response<TrendingResp>
    suspend fun getSearchHistory(): List<SearchHistoryEntity>
    suspend fun addSearchHistory(searchKeyword: SearchHistoryEntity)
    suspend fun searchShops(
        keyword: String,
        x: Double,
        y: Double,
        page: Int,
        size: Int
    ): Response<ShopsResp>
}
