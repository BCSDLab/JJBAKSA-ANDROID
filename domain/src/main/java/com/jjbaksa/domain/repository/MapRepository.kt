package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.follower.FollowerShopReview
import com.jjbaksa.domain.resp.map.MapShopData
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.resp.map.ShopReview
import com.jjbaksa.domain.resp.map.ShopReviewLastDate
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double,
        onError: (String) -> Unit
    ): Flow<Result<MapShopData>>
    suspend fun getShopDetail(
        placeId: String,
        onError: (String) -> Unit
    ): Flow<Result<ShopDetail>>
    suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<String>
    ): Flow<Result<ShopReview>>
    suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<ShopMyReview>>
    suspend fun getShopReviewLastDate(placeId: String): Flow<Result<ShopReviewLastDate>>
    suspend fun getShopFollowerReviewLastDate(placeId: String): Flow<Result<ShopReviewLastDate>>
    suspend fun getFollowerShopReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Flow<Result<FollowerShopReview>>
}
