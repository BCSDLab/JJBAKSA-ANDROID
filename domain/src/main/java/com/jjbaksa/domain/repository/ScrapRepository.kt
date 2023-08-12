package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.scrap.AddShopScrap
import com.jjbaksa.domain.resp.scrap.ShopScrap
import kotlinx.coroutines.flow.Flow

interface ScrapRepository {
    suspend fun getShopScrap(
        scrapId: Int
    ): Flow<Result<ShopScrap>>

    suspend fun addShopScrap(directoryId: Int, placeId: String): Flow<Result<AddShopScrap>>
}
