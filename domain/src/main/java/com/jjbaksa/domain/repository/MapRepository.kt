package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.map.MapShopData
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double
    ): Flow<Result<MapShopData>>
}
