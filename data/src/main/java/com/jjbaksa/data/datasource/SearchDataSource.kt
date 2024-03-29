package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.TrendResp
import retrofit2.Response

interface SearchDataSource {
    suspend fun getTrending(): Response<TrendResp>
    suspend fun getSearchKeyword(word: String): Response<AutoKeywordResp>
    suspend fun getShops(keyword: String, locationBody: LocationBody): Response<SearchShopResp>
    suspend fun getShopsPage(pageToken: String, locationBody: LocationBody): Response<SearchShopResp>
}
