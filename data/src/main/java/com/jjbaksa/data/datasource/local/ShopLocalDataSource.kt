package com.jjbaksa.data.datasource.local

import android.content.Context
import com.jjbaksa.data.database.SearchHistoryDao
import com.jjbaksa.data.datasource.ShopDataSource
import com.jjbaksa.data.entity.SearchHistoryEntity
import com.jjbaksa.data.model.shop.TrendingResp
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class ShopLocalDataSource @Inject constructor(
    @ApplicationContext context: Context,
    private val searchHistoryDao: SearchHistoryDao
) : ShopDataSource {
    override suspend fun getTrendings(): Response<TrendingResp> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchHistory(): List<SearchHistoryEntity> {
        return searchHistoryDao.getAll()
    }

    override suspend fun addSearchHistory(searchKeyword: SearchHistoryEntity) {
        searchHistoryDao.insert(searchKeyword)
    }
}
