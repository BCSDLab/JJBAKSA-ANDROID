package com.jjbaksa.data.repository

import android.net.Uri
import com.jjbaksa.data.datasource.remote.MapRemoteDataSource
import com.jjbaksa.data.mapper.FormDataUtil
import com.jjbaksa.data.mapper.toMapShopData
import com.jjbaksa.data.mapper.toShopDetail
import com.jjbaksa.data.mapper.toShopMyReview
import com.jjbaksa.data.mapper.toShopReview
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.domain.repository.MapRepository
import com.jjbaksa.domain.resp.map.MapShopData
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.resp.map.ShopReview
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
            call = {
                mapRemoteDataSource.getMapShop(
                    optionsFriend,
                    optionsNearby,
                    optionsScrap,
                    LocationBody(lat, lng)
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toMapShopData()
                } else {
                    MapShopData()
                }
            }
        )
    }

    override suspend fun getShopDetail(placeId: String): Flow<Result<ShopDetail>> {
        return apiCall(
            call = {
                mapRemoteDataSource.getShopDetail(placeId)
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopDetail()
                } else {
                    ShopDetail()
                }
            }
        )
    }

    override suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<String>
    ): Flow<Result<ShopReview>> {
        return apiCall(
            call = {
                val filesBody = reviewImages.map {
                    FormDataUtil.getImageBody("reviewImages", Uri.parse(it))
                }
                mapRemoteDataSource.setReview(placeId, content, rate, filesBody)
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopReview()
                } else {
                    ShopReview()
                }
            }
        )
    }

    override suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<ShopMyReview>> {
        return apiCall(
            call = {
                mapRemoteDataSource.getMyReview(
                    placeId,
                    idCursor,
                    dateCursor,
                    rateCursor,
                    size,
                    direction,
                    sort
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopMyReview()
                } else {
                    ShopMyReview()
                }
            }
        )
    }
}
