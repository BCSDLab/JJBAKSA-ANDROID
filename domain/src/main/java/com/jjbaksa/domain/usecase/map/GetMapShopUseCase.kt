package com.jjbaksa.domain.usecase.map

import com.jjbaksa.domain.repository.MapRepository
import com.jjbaksa.domain.resp.map.MapShopData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMapShopUseCase @Inject constructor(
    private val mapShopRepository: MapRepository
) {
    suspend operator fun invoke(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double
    ): Flow<Result<MapShopData>> {
        return mapShopRepository.getMapShop(optionsFriend, optionsNearby, optionsScrap, lat, lng)
    }
}
