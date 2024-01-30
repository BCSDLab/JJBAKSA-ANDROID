package com.jjbaksa.domain.usecase.shop

import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.model.shop.ShopsMaps
import com.jjbaksa.domain.model.shop.ShopDetail
import com.jjbaksa.domain.model.shop.ShopInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShopUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend operator fun invoke(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double,
        onError: (String) -> Unit
    ): Flow<Result<ShopsMaps>> {
        return shopRepository.getShopsMaps(optionsFriend, optionsNearby, optionsScrap, lat, lng, onError)
    }

    suspend fun getShopDetail(placeId: String, onError: (String) -> Unit): Flow<Result<ShopDetail>> {
        return shopRepository.getShopDetail(placeId, onError)
    }

    suspend fun getShopsRates(placeId: String, onError: (String) -> Unit): Flow<Result<Float>> {
        return shopRepository.getShopsRates(placeId, onError)
    }

    suspend fun getShopsScraps(placeId: String, onError: (String) -> Unit): Flow<Result<Long>> {
        return shopRepository.getShopsScraps(placeId, onError)
    }
}
