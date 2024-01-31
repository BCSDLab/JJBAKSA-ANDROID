package com.jjbaksa.data.mapper

import com.google.gson.Gson
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.model.shop.PeriodResp
import com.jjbaksa.data.model.shop.ShopsMapsResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.ShopResp
import com.jjbaksa.data.model.shop.CoordinateResp
import com.jjbaksa.data.model.shop.ShopInfoResp
import com.jjbaksa.data.model.shop.ShopRatesResp
import com.jjbaksa.data.model.shop.TimeResp
import com.jjbaksa.data.model.user.UserListResp
import com.jjbaksa.domain.ErrorResp
import com.jjbaksa.domain.model.shop.ShopsMapsContent
import com.jjbaksa.domain.model.shop.ShopsMaps
import com.jjbaksa.domain.model.shop.ShopDetail
import com.jjbaksa.domain.model.search.Shop
import com.jjbaksa.domain.model.search.ShopData
import com.jjbaksa.domain.model.shop.Coordinate
import com.jjbaksa.domain.model.shop.Period
import com.jjbaksa.domain.model.shop.ShopInfo
import com.jjbaksa.domain.model.shop.ShopRates
import com.jjbaksa.domain.model.shop.Time
import com.jjbaksa.domain.model.user.UserList

object RespMapper {
    fun errorMapper(json: String): ErrorResp {
        val startIdx = json.indexOf(",\"errorTrace")
        var newJson = json.substring(0, startIdx) + "}"
        val gson = Gson()
        val errorResp = gson.fromJson(newJson, ErrorResp::class.java)
        return errorResp
    }
}

fun SearchShopResp.toShopData() = ShopData(
    pageToken = pageToken ?: "",
    shopQueryResponseList = shopQueryResponseList?.map { it.toShop() }?.toMutableList()
        ?: mutableListOf()
)

fun ShopResp.toShop() = Shop(
    dist = dist ?: 0.0,
    category = category ?: "",
    formattedAddress = formattedAddress ?: "",
    lat = lat ?: 0.0,
    lng = lng ?: 0.0,
    name = name ?: "",
    openNow = openNow ?: false,
    photoToken = photoToken ?: "",
    placeId = placeId ?: "",
    ratingCount = ratingCount ?: 0,
    totalRating = totalRating ?: 0
)

fun List<ShopsMapsResp>?.toMapShopData() = ShopsMaps(
    shopsMapsContent = this?.map { it.toMapShopContent() }.orEmpty()
)

fun ShopsMapsResp.toMapShopContent() = ShopsMapsContent(
    placeId = placeId ?: "",
    name = name ?: "",
    lat = coordinate.lat ?: 0.0,
    lng = coordinate.lng ?: 0.0,
    photo = photo ?: emptyList(),
    totalRating = rate?.totalRating,
    ratingCount = rate?.ratingCount,
    openNow = openNow,
    formattedAddress = formattedAddress,
    simpleFormattedAddress = simpleFormattedAddress,
)

fun PeriodResp.toPeriod() = Period(
    open = open?.toTime() ?: Time(),
    close = close?.toTime() ?: Time()
)

fun TimeResp.toTime() = Time(
    hour = hour ?: 0,
    minute = minute ?: 0
)

fun ShopDetailResp.toShopDetail() = ShopDetail(
    shopId = shopId ?: 0,
    placeId = placeId ?: "",
    category = category ?: "",
    name = name ?: "",
    totalRating = totalRating ?: 0,
    ratingCount = ratingCount ?: 0,
    scrap = scrap ?: 0,
    photos = photos ?: emptyList(),
    formattedAddress = formattedAddress ?: "",
    formattedPhoneNumber = formattedPhoneNumber ?: "",
    openNow = openNow ?: false,
    period = period?.map { it.toPeriod() } ?: listOf(Period()),
    lat = lat ?: 0.0,
    lng = lng ?: 0.0
)

fun UserListResp.toUserList() = UserList(
    content = content?.map { it.toUser() }.orEmpty()
)

fun CoordinateResp.toCoordinate() = Coordinate(
    lat = lat ?: 0.0,
    lng = lng ?: 0.0
)

fun ShopInfoResp.toShopInfo() = ShopInfo(
    placeId = placeId ?: "",
    category = category ?: "",
    name = name ?: "",
    photos = photos ?: emptyList(),
    formattedAddress = formattedAddress ?: "",
    coordinate = coordinate?.toCoordinate() ?: Coordinate(),
    formattedPhoneNumber = formattedPhoneNumber ?: "",
    todayPeriod = todayPeriod?.toPeriod() ?: Period()
)

fun ShopRatesResp.toShopRates() = ShopRates(
    totalRating = totalRating ?: 0,
    ratingCount = ratingCount ?: 0
)
