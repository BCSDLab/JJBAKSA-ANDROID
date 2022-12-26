package com.jjbaksa.data.datasource.remote

import android.util.Log
import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.ShopDataSource
import com.jjbaksa.data.entity.SearchHistoryEntity
import com.jjbaksa.data.model.shop.ShopsResp
import com.jjbaksa.data.model.shop.TrendingResp
import retrofit2.Response
import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : ShopDataSource {
    override suspend fun getTrendings(): Response<TrendingResp> {
        return authApi.trending()
    }

    override suspend fun getSearchHistory(): List<SearchHistoryEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun addSearchHistory(searchKeyword: SearchHistoryEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun searchShops(
        keyword: String,
        x: Double,
        y: Double,
        page: Int,
        size: Int
    ): Response<ShopsResp> {
        val tmp = authApi.shops(keyword, x, y, page, size)
        Log.d("Test", tmp.toString())
        return tmp
    }
}
