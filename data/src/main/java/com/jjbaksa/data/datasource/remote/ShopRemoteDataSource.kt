package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.ShopDataSource
import com.jjbaksa.data.model.shop.TrendingResp
import retrofit2.Response
import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : ShopDataSource {
    override suspend fun getTrendings(): Response<TrendingResp> {
        return authApi.trending()
    }
}