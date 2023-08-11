package com.jjbaksa.data.api

import com.jjbaksa.data.model.pin.ShopDetailResp
import retrofit2.Response
import retrofit2.http.GET

interface TestNoAuthApi {
    @GET("v3/10ef369e-a221-4925-a4dd-1c5ec5a6992f")
    suspend fun getShopDetail(
    ): Response<ShopDetailResp>
}