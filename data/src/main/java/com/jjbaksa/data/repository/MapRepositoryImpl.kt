package com.jjbaksa.data.repository

import android.util.Log
import com.jjbaksa.data.datasource.remote.MapRemoteDataSource
import com.jjbaksa.data.mapper.toMapShopData
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.domain.repository.MapRepository
import com.jjbaksa.domain.resp.map.MapShopData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapRemoteDataSource: MapRemoteDataSource
) : MapRepository {
    override suspend fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double
    ): Flow<Result<MapShopData>> {
        return apiCall(
            call = { mapRemoteDataSource.getMapShop(optionsFriend, optionsNearby, optionsScrap, LocationBody(lat, lng)) },
            mapper = {
                if (it.isSuccessful) {
                    Log.d("로그", "MapRepositoryImpl isSuccessful")
                    it.body()!!.toMapShopData()
                } else {
                    Log.d("로그", "MapRepositoryImpl fail")
                    MapShopData()
                }
            }
        )
    }
}
