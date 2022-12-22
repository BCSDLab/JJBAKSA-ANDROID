package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.ShopLocalDataSource
import com.jjbaksa.data.datasource.remote.ShopRemoteDataSource
import com.jjbaksa.data.entity.SearchHistoryEntity
import com.jjbaksa.data.mapper.SearchHistoryMapper.mapToSearchHistoryResult
import com.jjbaksa.data.mapper.TrendingMapper.mapToTrendingResult
import com.jjbaksa.domain.resp.shop.ShopsResp
import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.resp.shop.TrendingResult
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopRemoteDataSource: ShopRemoteDataSource,
    private val shopLocalDataSource: ShopLocalDataSource
) : ShopRepository {
    override suspend fun getTrendings(): TrendingResult? {
        val resp = shopRemoteDataSource.getTrendings().body()
        return resp?.mapToTrendingResult()
    }

    override suspend fun getSearchHistory(): List<String> {
        val resp = shopLocalDataSource.getSearchHistory()
        return resp.mapToSearchHistoryResult()
    }

    override suspend fun addSearchHistory(searchKeyword: String) {
        shopLocalDataSource.addSearchHistory(SearchHistoryEntity(searchKeyword))
    }

    override suspend fun searchShops(
        keyword: String,
        x: Double,
        y: Double,
        page: Int,
        size: Int
    ): ShopsResp? {
        return shopRemoteDataSource.searchShops(keyword, x, y, page, size).body()
    }
}
