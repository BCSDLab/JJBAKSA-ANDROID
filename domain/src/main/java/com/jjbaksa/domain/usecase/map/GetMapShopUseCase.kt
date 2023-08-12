package com.jjbaksa.domain.usecase.map

import com.jjbaksa.domain.repository.MapRepository
import com.jjbaksa.domain.resp.follower.FollowerShopReview
import com.jjbaksa.domain.resp.map.MapShopData
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.resp.map.ShopReview
import com.jjbaksa.domain.resp.map.ShopReviewLastDate
import com.jjbaksa.domain.resp.scrap.ShopScrap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMapShopUseCase @Inject constructor(
    private val mapShopRepository: MapRepository
) {
    suspend operator fun invoke(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double
    ): Flow<Result<MapShopData>> {
        return mapShopRepository.getMapShop(optionsFriend, optionsNearby, optionsScrap, lat, lng)
    }

    suspend fun getShopDetail(placeId: String): Flow<Result<ShopDetail>> {
        return mapShopRepository.getShopDetail(placeId)
    }

    suspend fun setReview(
        placeId: String,
        content: String,
        rate: Int,
        reviewImages: List<String>
    ): Flow<Result<ShopReview>> {
        return mapShopRepository.setReview(placeId, content, rate, reviewImages)
    }

    suspend fun getMyReview(
        placeId: String,
        idCursor: Int? = null,
        dateCursor: String? = null,
        rateCursor: Int? = null,
        size: Int? = null, // 1~10
        direction: String? = null, // asc / desc
        sort: String? = null // createdAt / rate
    ): Flow<Result<ShopMyReview>> {
        return mapShopRepository.getMyReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
    }

    suspend fun getShopReviewLastDate(placeId: String): Flow<Result<ShopReviewLastDate>> {
        return mapShopRepository.getShopReviewLastDate(placeId)
    }
    suspend fun getShopFollowerReviewLastDate(placeId: String): Flow<Result<ShopReviewLastDate>> {
        return mapShopRepository.getShopFollowerReviewLastDate(placeId)
    }

    suspend fun getFollowerShopReview(
        placeId: String,
        idCursor: Int? = null,
        dateCursor: String? = null,
        rateCursor: Int? = null,
        size: Int? = null,
        direction: String? = null,
        sort: String? = null
    ): Flow<Result<FollowerShopReview>> {
        return mapShopRepository.getFollowerShopReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
    }
}
