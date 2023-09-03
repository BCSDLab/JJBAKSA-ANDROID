package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.search.ShopData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getTrendText(): Flow<Result<List<String>>>
    suspend fun getSearchKeyword(word: String): Flow<Result<List<String>>>
    suspend fun getShops(
        keyword: String,
        lat: Double,
        lng: Double,
    ): Flow<Result<ShopData>>
    suspend fun getShopsPage(
        pageToken: String,
        lat: Double,
        lng: Double,
    ): Flow<Result<ShopData>>
}
