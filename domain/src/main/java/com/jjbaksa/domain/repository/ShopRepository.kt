package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.shop.ShopsMaps
import com.jjbaksa.domain.model.shop.ShopDetail
import com.jjbaksa.domain.model.shop.ShopInfo
import com.jjbaksa.domain.model.shop.ShopRates
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    suspend fun getShopsMaps(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double,
        onError: (String) -> Unit
    ): Flow<Result<ShopsMaps>>
    suspend fun getShopDetail(
        placeId: String,
        onError: (String) -> Unit
    ): Flow<Result<ShopDetail>>
    suspend fun getShopsRates(
        placeId: String,
        onError: (String) -> Unit
    ): Flow<Result<Float>>
    suspend fun getShopsScraps(
        placeId: String,
        onError: (String) -> Unit
    ): Flow<Result<Long>>

    suspend fun getShopInfo(
        placeId: String,
        onError: (String) -> Unit
    ): Flow<Result<ShopInfo>>

    suspend fun getShopRates(
        placeId: String,
        onError: (String) -> Unit
    ): Flow<Result<ShopRates>>
}
