package com.jjbaksa.data.api

import com.jjbaksa.data.model.pin.ShopDetailResp
import retrofit2.Response
import retrofit2.http.GET

interface TestNoAuthApi {
    /**
     * AuthApi getShopDetail TEST
     */
    @GET("v3/330d86e7-1702-4ae0-a39a-e2ba6b8fe431")
    suspend fun getShopDetail(): Response<ShopDetailResp>
}
