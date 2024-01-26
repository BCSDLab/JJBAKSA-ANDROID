package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.local.SearchLocalDataSource
import com.jjbaksa.data.datasource.remote.SearchRemoteDataSource
import com.jjbaksa.data.mapper.toShopData
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.domain.repository.SearchRepository
import com.jjbaksa.domain.model.search.ShopData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchLocalDataSource: SearchLocalDataSource
) : SearchRepository {
    override suspend fun getTrendText(): Flow<Result<List<String>>> {
        return apiCall(
            call = { searchRemoteDataSource.getTrending() },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.trendings
                } else {
                    listOf()
                }
            }
        )
    }

    override suspend fun getSearchKeyword(
        word: String,
        lat: Double,
        lng: Double
    ): Flow<Result<List<String>>> {
        return apiCall(
            call = { searchRemoteDataSource.getSearchKeyword(word, LocationBody(lat, lng)) },
            mapper = {
                if (it.isSuccessful) {
                    it.body() ?: listOf()
                } else {
                    listOf()
                }
            }
        )
    }

    override suspend fun getShops(
        keyword: String,
        lat: Double,
        lng: Double
    ): Flow<Result<ShopData>> {
        return apiCall(
            call = { searchRemoteDataSource.getShops(keyword, LocationBody(lat, lng)) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopData()
                } else {
                    ShopData()
                }
            }
        )
    }

    override suspend fun getShopsPage(
        pageToken: String,
        lat: Double,
        lng: Double,
    ): Flow<Result<ShopData>> {
        return apiCall(
            call = { searchRemoteDataSource.getShopsPage(pageToken, LocationBody(lat, lng)) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopData()
                } else {
                    ShopData()
                }
            }
        )
    }

    override fun getSearchHistory(): String {
        return searchLocalDataSource.getSearchHistory()
    }

    override suspend fun setSearchHistories(resultJsonString: String) {
        searchLocalDataSource.setSearchHistories(resultJsonString)
    }
}
