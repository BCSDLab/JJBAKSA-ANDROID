package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.datasource.MapDataSource
import com.jjbaksa.data.model.map.MapShopResp
import com.jjbaksa.data.model.search.LocationBody
import retrofit2.Response
import javax.inject.Inject

class MapRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi
) : MapDataSource {
    override suspend fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        location: LocationBody
    ): Response<List<MapShopResp>> {
        return noAuthApi.getMapShop(optionsFriend, optionsNearby, optionsScrap, location)
    }
}
