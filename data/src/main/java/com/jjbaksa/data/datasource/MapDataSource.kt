package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.map.MapShopResp
import com.jjbaksa.data.model.search.LocationBody
import retrofit2.Response

interface MapDataSource {
    suspend fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        location: LocationBody
    ): Response<List<MapShopResp>>
}
