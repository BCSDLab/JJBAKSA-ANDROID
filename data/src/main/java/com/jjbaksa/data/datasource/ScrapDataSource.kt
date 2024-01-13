package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.scrap.AddShopScrapResp
import com.jjbaksa.data.model.scrap.ScrapsResp
import com.jjbaksa.data.model.scrap.ShopScrapResp
import retrofit2.Response

interface ScrapDataSource {
    suspend fun getShopScrap(
        scrapId: Int
    ): Response<ShopScrapResp>
    suspend fun addShopScrap(
        directoryId: Int,
        placeId: String
    ): Response<AddShopScrapResp>
    suspend fun getScraps(
        user: Int?,
        cursor: Int?,
        size: Int
    ): Response<ScrapsResp>
    suspend fun deleteShopScrap(
        scrapId: Int
    ): Response<Unit>
}
