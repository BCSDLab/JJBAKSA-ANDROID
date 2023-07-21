package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.SearchRemoteDataSource
import com.jjbaksa.data.mapper.toAutoKeyword
import com.jjbaksa.data.mapper.toShopData
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.domain.repository.SearchRepository
import com.jjbaksa.domain.resp.search.ShopData
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchRemoteDataSource: SearchRemoteDataSource): SearchRepository {
    override suspend fun getTrendText(onSuccess: (List<String>) -> Unit) {
        var result = searchRemoteDataSource.getTrending()
        if (result.isSuccessful) {
            onSuccess(result.body()!!.trendings)
        }
    }

    override suspend fun getSearchKeyword(
        word: String, onSuccess: (List<String>) -> Unit, onError: () -> Unit
    ) {
        var result = searchRemoteDataSource.getSearchKeyword(word)
        if (result.isSuccessful) {
            var resp = result.body()!!.toAutoKeyword()
            onSuccess(resp.autoCompletes)
        } else {
            onError()
        }
    }

    override suspend fun getShops(
        keyword: String,
        lat: Double,
        lng: Double,
        onSuccess: (ShopData) -> Unit,
        onError: () -> Unit
    ) {
        var result = searchRemoteDataSource.getShops(keyword, LocationBody(lat, lng))
        if (result.isSuccessful) {
            onSuccess(result.body()!!.toShopData())
        } else {
            onError()
        }
    }

    override suspend fun getShopsPage(
        pageToken: String,
        lat: Double,
        lng: Double,
        onSuccess: (ShopData) -> Unit,
        onError: () -> Unit
    ) {
        var result = searchRemoteDataSource.getShopsPage(pageToken, LocationBody(lat, lng))
        if (result.isSuccessful) {
            onSuccess(result.body()!!.toShopData())
        } else {
            onError()
        }
    }
}