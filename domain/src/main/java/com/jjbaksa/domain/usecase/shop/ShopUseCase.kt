package com.jjbaksa.domain.usecase.shop

import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.model.shop.ShopsMaps
import com.jjbaksa.domain.model.shop.ShopDetail
import kotlinx.coroutines.flow.Flow
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
}
