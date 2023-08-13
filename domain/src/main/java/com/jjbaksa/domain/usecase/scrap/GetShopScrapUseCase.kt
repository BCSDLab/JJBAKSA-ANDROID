package com.jjbaksa.domain.usecase.scrap

import com.jjbaksa.domain.repository.ScrapRepository
import com.jjbaksa.domain.resp.scrap.AddShopScrap
import com.jjbaksa.domain.resp.scrap.ShopScrap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShopScrapUseCase @Inject constructor(
    private val scrapRepository: ScrapRepository
) {
    suspend operator fun invoke(scrapId: Int): Flow<Result<ShopScrap>> {
        return scrapRepository.getShopScrap(scrapId)
    }

    suspend fun addShopScrap(directoryId: Int, placeId: String): Flow<Result<AddShopScrap>> {
        return scrapRepository.addShopScrap(directoryId, placeId)
    }
}