package com.jjbaksa.data.datasource.remote

import com.jjbaksa.data.api.AuthApi
import com.jjbaksa.data.api.NoAuthApi
import com.jjbaksa.data.api.TestNoAuthApi
import com.jjbaksa.data.datasource.MapDataSource
import com.jjbaksa.data.model.follower.FollowerShopReviewResp
import com.jjbaksa.data.model.map.MapShopResp
import com.jjbaksa.data.model.map.MyReviewResp
import com.jjbaksa.data.model.map.ShopReviewLastDateResp
import com.jjbaksa.data.model.map.ShopReviewResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.LocationBody
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class MapRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val noAuthApi: NoAuthApi,
    private val testNoAuthApi: TestNoAuthApi
) : MapDataSource {
    override suspend fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        location: LocationBody
    ): Response<List<MapShopResp>> {
        return testNoAuthApi.getMapShop()
//        return noAuthApi.getMapShop(optionsFriend, optionsNearby, optionsScrap, location)
    }

    override suspend fun getShopDetail(placeId: String): Response<ShopDetailResp> {
        return testNoAuthApi.getShopDetail()
//        return authApi.getShopDetail(placeId)
    }

    override suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<MultipartBody.Part>
    ): Response<ShopReviewResp> {
        return authApi.setReview(placeId, content, rate, reviewImages)
    }

    override suspend fun getMyReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Response<MyReviewResp> {
        return authApi.getMyReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
    }

    override suspend fun getShopReviewLastDate(placeId: String): Response<ShopReviewLastDateResp> {
        return authApi.getShopReviewLastDate(placeId)
    }

    override suspend fun getShopFollowerReviewLastDate(placeId: String): Response<ShopReviewLastDateResp> {
        return authApi.getShopFollowerReviewLastDate(placeId)
    }

    override suspend fun getFollowerShopReview(
        placeId: String,
        idCursor: Int?,
        dateCursor: String?,
        rateCursor: Int?,
        size: Int?,
        direction: String?,
        sort: String?
    ): Response<FollowerShopReviewResp> {
        return authApi.getFollowerShopReview(
            placeId,
            idCursor,
            dateCursor,
            rateCursor,
            size,
            direction,
            sort
        )
    }
}
