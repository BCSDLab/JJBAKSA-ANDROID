package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.scrap.AddShopScrap
import com.jjbaksa.domain.model.scrap.Scraps
import com.jjbaksa.domain.model.scrap.ShopScrap
import kotlinx.coroutines.flow.Flow

interface ScrapRepository {
    suspend fun getShopScrap(
        scrapId: Int
    ): Flow<Result<ShopScrap>>

    suspend fun addShopScrap(directoryId: Int, placeId: String): Flow<Result<AddShopScrap>>
    suspend fun getScraps(
        user: Int?,
        cursor: Int?,
        size: Int
    ): Flow<Result<Scraps>>
}
