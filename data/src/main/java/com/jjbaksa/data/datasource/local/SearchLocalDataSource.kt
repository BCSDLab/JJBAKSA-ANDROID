package com.jjbaksa.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.jjbaksa.data.database.SearchPreferenceKeys
import com.jjbaksa.data.database.userDataStore
import com.jjbaksa.data.datasource.SearchDataSource
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.TrendResp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Response

class SearchLocalDataSource(
    @ApplicationContext context: Context,
) : SearchDataSource {
    private val dataStore = context.userDataStore
    override suspend fun getTrending(): Response<TrendResp> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchKeyword(
        word: String,
        locationBody: LocationBody,
    ): Response<AutoKeywordResp> {
        TODO("Not yet implemented")
    }

    override suspend fun getShops(
        keyword: String,
        locationBody: LocationBody,
    ): Response<SearchShopResp> {
        TODO("Not yet implemented")
    }

    override suspend fun getShopsPage(
        pageToken: String,
        locationBody: LocationBody,
    ): Response<SearchShopResp> {
        TODO("Not yet implemented")
    }


    override suspend fun getSearchHistory(): String {
        return runBlocking {
            dataStore.data.first()[SearchPreferenceKeys.SEARCH_HISTORY] ?: "[]"
        }
    }

    override suspend fun saveSearchHistory(keyword: String) {
        dataStore.edit {
            it[SearchPreferenceKeys.SEARCH_HISTORY] = keyword
        }
    }

    override suspend fun deleteSearchHistory(keyword: String) {
        TODO("DELETE SEARCH HISTORY")
    }
}