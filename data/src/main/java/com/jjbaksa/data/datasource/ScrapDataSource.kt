package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.scrap.AddShopScrapResp
import com.jjbaksa.data.model.scrap.ShopScrapResp
import retrofit2.Response

interface ScrapDataSource {
    suspend fun getShopScrap(
        scrapId: Int
    ): Response<ShopScrapResp>
    suspend fun addShopScrap(
        directoryId:Int,
        placeId: String
    ): Response<AddShopScrapResp>
}
