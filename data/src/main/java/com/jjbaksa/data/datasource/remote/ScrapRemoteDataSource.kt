package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.api.TestNoAuthApi
import com.jjbaksa.data.datasource.ScrapDataSource
import com.jjbaksa.data.model.scrap.AddShopScrapBodyReq
import com.jjbaksa.data.model.scrap.AddShopScrapResp
import com.jjbaksa.data.model.scrap.GetScrapsResp
import com.jjbaksa.data.model.scrap.ShopScrapResp
import retrofit2.Response
import javax.inject.Inject

class ScrapRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi,
    private val testNoAuthApi: TestNoAuthApi
) : ScrapDataSource {
    override suspend fun getShopScrap(scrapId: Int): Response<ShopScrapResp> {
        return authApi.getShopScrap(scrapId)
    }

    override suspend fun addShopScrap(
        directoryId: Int,
        placeId: String
    ): Response<AddShopScrapResp> {
        return authApi.postShopScrap(AddShopScrapBodyReq(directoryId, placeId))
    }

    override suspend fun getScraps(user: Int?, cursor: Int?, size: Int): Response<GetScrapsResp> {
//        return testNoAuthApi.getScraps()
        return authApi.getScraps(user, cursor, size)
    }
}
