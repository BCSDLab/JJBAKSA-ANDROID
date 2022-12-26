package com.jjbaksa.domain.repository

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.shop.ShopsResult
import com.jjbaksa.domain.resp.shop.TrendingResult

interface ShopRepository {
    suspend fun getTrendings(): RespResult<TrendingResult>
    suspend fun getSearchHistory(): List<String>
    suspend fun addSearchHistory(searchKeyword: String)
    suspend fun searchShops(
        keyword: String,
        x: Double,
        y: Double,
        page: Int,
        size: Int,
    ): RespResult<ShopsResult>
}
