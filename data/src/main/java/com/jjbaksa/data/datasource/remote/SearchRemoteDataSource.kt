package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.SearchDataSource
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.TrendResp
import retrofit2.Response
import javax.inject.Inject

class SearchRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : SearchDataSource {
    override suspend fun getTrending(): Response<TrendResp> {
        return noAuthApi.getTrending()
    }

    override suspend fun getSearchKeyword(word: String): Response<AutoKeywordResp> {
        return noAuthApi.getSearchKeyword(word)
    }

    override suspend fun getShops(
        keyword: String,
        locationBody: LocationBody
    ): Response<SearchShopResp> {
        return noAuthApi.getShops(keyword, locationBody)
    }

    override suspend fun getShopsPage(
        pageToken: String,
        locationBody: LocationBody
    ): Response<SearchShopResp> {
        return noAuthApi.getShopsPage(pageToken, locationBody)
    }
}
