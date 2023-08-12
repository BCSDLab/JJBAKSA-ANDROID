package com.jjbaksa.data.api

import com.jjbaksa.data.model.pin.ShopDetailResp
import retrofit2.Response
import retrofit2.http.GET

interface TestNoAuthApi {
    @GET("v3/aa8a9ed8-f512-42a0-af21-664a6c102fdb")
    suspend fun getShopDetail(): Response<ShopDetailResp>
}
