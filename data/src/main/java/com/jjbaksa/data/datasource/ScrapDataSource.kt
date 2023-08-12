package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.scrap.ShopScrapResp
import retrofit2.Response

interface ScrapDataSource {
    suspend fun getShopScrap(
        scrapId: Int
    ): Response<ShopScrapResp>
}
