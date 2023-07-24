package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.SearchRepository
import com.jjbaksa.domain.resp.search.ShopData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchShopUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun firstSearch(keyword: String, lat: Double, lng: Double,): Flow<Result<ShopData>> {
        return searchRepository.getShops(keyword, lat, lng)
    }
    suspend fun pagingSearch(pageToken: String, lat: Double, lng: Double,): Flow<Result<ShopData>> {
        return searchRepository.getShopsPage(pageToken, lat, lng)
    }
}
