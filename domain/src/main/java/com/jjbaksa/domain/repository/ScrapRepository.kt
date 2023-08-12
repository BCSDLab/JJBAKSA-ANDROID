package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.scrap.ShopScrap
import kotlinx.coroutines.flow.Flow

interface ScrapRepository {
    suspend fun getShopScrap(
        scrapId: Int
    ): Flow<Result<ShopScrap>>
}
