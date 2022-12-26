package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.ShopLocalDataSource
import com.jjbaksa.data.datasource.remote.ShopRemoteDataSource
import com.jjbaksa.data.entity.SearchHistoryEntity
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.data.mapper.SearchHistoryMapper.mapToSearchHistoryResult
import com.jjbaksa.data.mapper.TrendingMapper.mapTrendingToResult
import com.jjbaksa.data.mapper.ShopMapper.mapShopToResult
import com.jjbaksa.domain.resp.shop.ShopsResult
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.resp.shop.TrendingResult
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopRemoteDataSource: ShopRemoteDataSource,
    private val shopLocalDataSource: ShopLocalDataSource
) : ShopRepository {
    override suspend fun getTrendings(): RespResult<TrendingResult> {
        val resp = shopRemoteDataSource.getTrendings()
        return if (resp.isSuccessful) {
            resp.body()!!.mapTrendingToResult()
        } else {
            val errorBodyJson = resp.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
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
    ): RespResult<ShopsResult> {
        val resp = shopRemoteDataSource.searchShops(keyword, x, y, page, size)
        return if (resp.isSuccessful) {
            resp.body()!!.mapShopToResult()
        } else {
            val errorBodyJson = resp.errorBody()!!.string()
            val errorBody = RespMapper.errorMapper(errorBodyJson)
            RespResult.Error(ErrorType(errorBody.errorMessage, errorBody.code))
        }
    }
}
