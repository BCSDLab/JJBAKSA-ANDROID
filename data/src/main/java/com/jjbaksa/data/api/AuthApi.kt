package com.jjbaksa.data.api

import com.jjbaksa.domain.resp.shop.ShopsResp
import com.jjbaksa.data.model.shop.TrendingResp
import com.jjbaksa.data.model.user.UserResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @GET("user/me")
    suspend fun userMe(): Response<UserResp>

    @GET("trending")
    suspend fun trending(): Response<TrendingResp>

    @POST("shops")
    suspend fun shops(
        @Query("keyword") keyword: String,
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<ShopsResp>
}
