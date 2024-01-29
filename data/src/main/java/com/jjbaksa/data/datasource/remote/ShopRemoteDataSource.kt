package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.ShopDataSource
import com.jjbaksa.data.model.shop.ShopsMapsResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.LocationBody
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
        location: LocationBody
    ): Response<List<ShopsMapsResp>> {
        return authApi.getShopsMaps(optionsFriend, optionsNearby, optionsScrap, location)
    }

    override suspend fun getShopDetail(placeId: String): Response<ShopDetailResp> {
        return authApi.getShopDetail(placeId)
    }
}
