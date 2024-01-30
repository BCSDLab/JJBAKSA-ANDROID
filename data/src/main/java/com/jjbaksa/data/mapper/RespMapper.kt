package com.jjbaksa.data.mapper

import com.google.gson.Gson
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.model.pin.CloseResp
import com.jjbaksa.data.model.pin.OpenResp
import com.jjbaksa.data.model.pin.PeriodResp
import com.jjbaksa.data.model.shop.ShopsMapsResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.ShopResp
import com.jjbaksa.data.model.user.UserListResp
import com.jjbaksa.domain.ErrorResp
import com.jjbaksa.domain.model.shop.ShopsMapsContent
import com.jjbaksa.domain.model.shop.ShopsMaps
import com.jjbaksa.domain.model.shop.ShopDetail
import com.jjbaksa.domain.model.search.AutoKeyword
import com.jjbaksa.domain.model.search.Shop
import com.jjbaksa.domain.model.search.ShopData
import com.jjbaksa.domain.model.shop.Close
import com.jjbaksa.domain.model.shop.Open
import com.jjbaksa.domain.model.shop.Period
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

fun AutoKeywordResp.toAutoKeyword() = AutoKeyword(
    autoCompletes = autoCompletes ?: listOf()
)

fun SearchShopResp.toShopData() = ShopData(
    pageToken = pageToken ?: "",
    shopQueryResponseList = shopQueryResponseList?.map { it.toShop() }?.toMutableList()
        ?: mutableListOf<Shop>()
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
    lat = geometry.location?.lat ?: 0.0,
    lng = geometry.location?.lng ?: 0.0,
    photo = photo ?: "",
)

fun PeriodResp.toPeriod() = Period(
    open = open?.toOpen() ?: Open(),
    close = close?.toClose() ?: Close()
)

fun OpenResp.toOpen() = Open(
    day = day ?: 0,
    time = time ?: 0
)

fun CloseResp.toClose() = Close(
    day = day ?: 0,
    time = time ?: 0
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
