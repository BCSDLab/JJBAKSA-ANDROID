package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.shop.ShopsMapsResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.data.model.shop.ShopInfoResp
import retrofit2.Response

interface ShopDataSource {
    suspend fun getShopsMaps(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        location: LocationBody
    ): Response<List<ShopsMapsResp>>
    suspend fun getShopDetail(
        placeId: String
    ): Response<ShopDetailResp>

    suspend fun getShopInfo(
        placeId: String
    ): Response<ShopInfoResp>
}
