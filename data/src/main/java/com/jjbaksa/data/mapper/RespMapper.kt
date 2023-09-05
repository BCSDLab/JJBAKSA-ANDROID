package com.jjbaksa.data.mapper

import com.google.gson.Gson
import com.jjbaksa.data.model.inquiry.InquiryResp
import com.jjbaksa.data.model.inquiry.dto.InquiryContentDTO
import com.jjbaksa.data.model.inquiry.dto.InquiryImagesDTO
import com.jjbaksa.data.model.shop.ShopsMapsResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.ShopResp
import com.jjbaksa.domain.ErrorResp
import com.jjbaksa.domain.model.inquiry.InquiryContent
import com.jjbaksa.domain.model.inquiry.InquiryData
import com.jjbaksa.domain.model.inquiry.InquiryImages
import com.jjbaksa.domain.model.shop.ShopsMapsContent
import com.jjbaksa.domain.model.shop.ShopsMaps
import com.jjbaksa.domain.model.shop.ShopDetail
import com.jjbaksa.domain.model.search.AutoKeyword
import com.jjbaksa.domain.model.search.Shop
import com.jjbaksa.domain.model.search.ShopData

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

fun InquiryResp.toInquiryData() = InquiryData(
    content = content?.map { it.toInquiryContent() }.orEmpty(),
    size = size ?: 0
)

fun InquiryContentDTO.toInquiryContent() = InquiryContent(
    id = id ?: 0,
    answer = answer ?: "",
    content = content ?: "",
    createdAt = createdAt ?: "",
    createdBy = createdBy ?: "",
    inquiryImages = inquiryImages?.map { it.toInquiryImages() }.orEmpty(),
    isSecreted = isSecreted ?: 0,
    title = title ?: ""
)

fun InquiryImagesDTO.toInquiryImages() = InquiryImages(
    imageUrl = imageUrl ?: "",
    originalName = originalName ?: "",
    path = path ?: ""
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

fun ShopDetailResp.toShopDetail() = ShopDetail(
    shopId = shopId ?: 0,
    placeId = placeId ?: "",
    category = category ?: "",
    name = name ?: "",
    totalRating = totalRating ?: 0,
    ratingCount = ratingCount ?: 0,
    scrap = scrap ?: 0,
    photos = photos ?: emptyList(),
)
