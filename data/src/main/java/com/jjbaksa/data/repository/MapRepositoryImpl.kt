package com.jjbaksa.data.repository

import android.net.Uri
import com.jjbaksa.data.datasource.remote.MapRemoteDataSource
import com.jjbaksa.data.mapper.FormDataUtil
import com.jjbaksa.data.mapper.RespMapper
import com.jjbaksa.data.mapper.toFollowerShopReview
import com.jjbaksa.data.mapper.toMapShopData
import com.jjbaksa.data.mapper.toShopDetail
import com.jjbaksa.data.mapper.toShopMyReview
import com.jjbaksa.data.mapper.toShopReview
import com.jjbaksa.data.mapper.toShopReviewLastDate
import com.jjbaksa.data.model.apiCall
import com.jjbaksa.data.model.search.LocationBody
import com.jjbaksa.domain.repository.MapRepository
import com.jjbaksa.domain.resp.follower.FollowerShopReview
import com.jjbaksa.domain.resp.map.MapShopData
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.resp.map.ShopReview
import com.jjbaksa.domain.resp.map.ShopReviewLastDate
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
        lng: Double,
        onError: (String) -> Unit
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
                    it.body()?.toMapShopData() ?: MapShopData()
                } else {
                    val errorResult = RespMapper.errorMapper(it.errorBody()?.string() ?: "")
                    onError(errorResult.errorMessage)
                    MapShopData()
                }
            }
        )
    }
    override suspend fun getShopDetail(placeId: String, onError: (String) -> Unit): Flow<Result<ShopDetail>> {
        return apiCall(
            call = {
                mapRemoteDataSource.getShopDetail(placeId)
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

    override suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<String>
    ): Flow<Result<ShopReview>> {
        return apiCall(
            call = {
                var filesBody = reviewImages.map {
                    FormDataUtil.getImageBody("reviewImages", Uri.parse(it))
                }
                if (filesBody.isNullOrEmpty()) {
                    filesBody = listOf(FormDataUtil.getEmptyBody())
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

    override suspend fun getShopReviewLastDate(placeId: String): Flow<Result<ShopReviewLastDate>> {
        return apiCall(
            call = { mapRemoteDataSource.getShopReviewLastDate(placeId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopReviewLastDate()
                } else {
                    ShopReviewLastDate()
                }
            }
        )
    }

    override suspend fun getShopFollowerReviewLastDate(placeId: String): Flow<Result<ShopReviewLastDate>> {
        return apiCall(
            call = { mapRemoteDataSource.getShopFollowerReviewLastDate(placeId) },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toShopReviewLastDate()
                } else {
                    ShopReviewLastDate()
                }
            }
        )
    }

    override suspend fun getFollowerShopReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<FollowerShopReview>> {
        return apiCall(
            call = {
                mapRemoteDataSource.getFollowerShopReview(
                    placeId, idCursor, dateCursor, rateCursor, size, direction, sort
                )
            },
            mapper = {
                if (it.isSuccessful) {
                    it.body()!!.toFollowerShopReview()
                } else {
                    FollowerShopReview()
                }
            }
        )
    }
}
