package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.ScrapDataSource
import com.jjbaksa.data.model.scrap.ShopScrapResp
import retrofit2.Response
import javax.inject.Inject

class ScrapRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
): ScrapDataSource {
    override suspend fun getShopScrap(scrapId: Int): Response<ShopScrapResp> {
        return authApi.getShopScrap(scrapId)
    }
}