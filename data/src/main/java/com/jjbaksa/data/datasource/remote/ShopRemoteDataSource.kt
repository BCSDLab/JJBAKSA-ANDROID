package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.model.trending.Trend
import retrofit2.Response
import javax.inject.Inject

class ShopRemoteDataSource @Inject constructor(
    private val api: AuthApi
) {
    suspend fun getTrendingKeyword(): Response<Trend> {
        return api.getTrendingKeyword()
    }
}
