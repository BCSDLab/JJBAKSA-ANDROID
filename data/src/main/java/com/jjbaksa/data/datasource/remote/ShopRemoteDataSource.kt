package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.ShopDataSource
import com.jjbaksa.data.model.pin.RateDto
import com.jjbaksa.data.model.pin.ScrapDto
import com.jjbaksa.data.model.shop.ShopsMapsResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.shop.ShopInfoResp
import com.jjbaksa.data.model.shop.ShopRatesResp
import retrofit2.Response
import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi,
) : ShopDataSource {
    override suspend fun getShopsMaps(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        location: LocationBody,
    ): Response<List<ShopsMapsResp>> {
        return authApi.getShopsMaps(optionsFriend, optionsNearby, optionsScrap, location)
    }

    override suspend fun getShopDetail(placeId: String): Response<ShopDetailResp> {
        return authApi.getShopDetail(placeId)
    }

    override suspend fun getShopsRates(placeId: String): Response<RateDto> {
        return authApi.getShopsRates(placeId)
    }

    override suspend fun getShopsScraps(placeId: String): Response<ScrapDto> {
        return authApi.getShopsScraps(placeId)
    }

    override suspend fun getShopInfo(placeId: String): Response<ShopInfoResp> {
        return noAuthApi.getShopInfo(placeId)
    }

    override suspend fun getShopRates(placeId: String): Response<ShopRatesResp> {
        return noAuthApi.getShopRates(placeId)
    }
}
