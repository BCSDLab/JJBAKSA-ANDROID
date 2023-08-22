package com.jjbaksa.data.api

import com.jjbaksa.data.model.map.MapShopResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.LocationBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TestNoAuthApi {
    /**
     * AuthApi getShopDetail TEST
     */
    @GET("v3/330d86e7-1702-4ae0-a39a-e2ba6b8fe431")
    suspend fun getShopDetail(): Response<ShopDetailResp>
    @GET("v3/b54d97a9-8a99-40a0-be17-f1b3fe5a8efd")
    suspend fun getMapShop(): Response<List<MapShopResp>>
}
