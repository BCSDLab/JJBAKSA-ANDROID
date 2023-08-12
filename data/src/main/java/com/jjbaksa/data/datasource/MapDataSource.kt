package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.follower.FollowerShopReviewResp
import com.jjbaksa.data.model.map.MapShopResp
import com.jjbaksa.data.model.map.MyReviewResp
import com.jjbaksa.data.model.map.ShopReviewLastDateResp
import com.jjbaksa.data.model.map.ShopReviewResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.LocationBody
import okhttp3.MultipartBody
import retrofit2.Response

interface MapDataSource {
    suspend fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        location: LocationBody
    ): Response<List<MapShopResp>>
    suspend fun getShopDetail(
        placeId: String
    ): Response<ShopDetailResp>
    suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<MultipartBody.Part>
    ): Response<ShopReviewResp>
    suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Response<MyReviewResp>
    suspend fun getShopReviewLastDate(placeId: String): Response<ShopReviewLastDateResp>
    suspend fun getShopFollowerReviewLastDate(placeId: String): Response<ShopReviewLastDateResp>
    suspend fun getFollowerShopReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Response<FollowerShopReviewResp>
}
