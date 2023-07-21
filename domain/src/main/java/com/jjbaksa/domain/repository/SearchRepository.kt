package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.search.ShopData

interface SearchRepository {
    suspend fun getTrendText(onSuccess: (List<String>) -> Unit)
    suspend fun getSearchKeyword(
        word: String,
        onSuccess: (List<String>) -> Unit,
        onError: () -> Unit
    )
    suspend fun getShops(
        keyword: String,
        lat: Double,
        lng: Double,
        onSuccess: (ShopData) -> Unit,
        onError: () -> Unit
    )
    suspend fun getShopsPage(
        pageToken: String,
        lat: Double,
        lng: Double,
        onSuccess: (ShopData) -> Unit,
        onError: () -> Unit
    )
}
