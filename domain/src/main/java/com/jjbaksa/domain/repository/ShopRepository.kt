package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.shop.ShopsResp
import com.jjbaksa.domain.resp.shop.TrendingResult

interface ShopRepository {
    suspend fun getTrendings(): TrendingResult?
    suspend fun getSearchHistory(): List<String>
    suspend fun addSearchHistory(searchKeyword: String)
    suspend fun searchShops(
        keyword: String,
        x: Double,
        y: Double,
        page: Int,
        size: Int,
    ): ShopsResp?
}
