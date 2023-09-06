package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.ShopRemoteDataSource
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.data.mapper.toMapShopData
import com.jjbaksa.data.mapper.toShopDetail
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.domain.repository.ShopRepository
import com.jjbaksa.domain.model.shop.ShopsMaps
import com.jjbaksa.domain.model.shop.ShopDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopRemoteDataSource: ShopRemoteDataSource
) : ShopRepository {
    override suspend fun getShopsMaps(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double,
        onError: (String) -> Unit
    ): Flow<Result<ShopsMaps>> {
        return apiCall(
            call = {
                shopRemoteDataSource.getShopsMaps(
                    optionsFriend,
                    optionsNearby,
                    optionsScrap,
                    LocationBody(lat, lng)
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toMapShopData() ?: ShopsMaps()
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    ShopsMaps()
                }
            }
        )
    }
    override suspend fun getShopDetail(placeId: String, onError: (String) -> Unit): Flow<Result<ShopDetail>> {
        return apiCall(
            call = {
                shopRemoteDataSource.getShopDetail(placeId)
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()?.toShopDetail() ?: ShopDetail()
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    ShopDetail()
                }
            }
        )
    }
}
