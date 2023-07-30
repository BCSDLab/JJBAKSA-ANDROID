package com.jjbaksa.data.mapper

import com.google.gson.Gson
import com.jjbaksa.data.model.inquiry.InquiryResp
import com.jjbaksa.data.model.inquiry.dto.InquiryContentDTO
import com.jjbaksa.data.model.inquiry.dto.InquiryImagesDTO
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.ShopResp
import com.jjbaksa.domain.BaseResp
import com.jjbaksa.domain.resp.inquiry.InquiryContent
import com.jjbaksa.domain.resp.inquiry.InquiryData
import com.jjbaksa.domain.resp.inquiry.InquiryImages
import com.jjbaksa.domain.resp.search.AutoKeyword
import com.jjbaksa.domain.resp.search.Shop
import com.jjbaksa.domain.resp.search.ShopData

object RespMapper {
    fun errorMapper(json: String): BaseResp {
        val startIdx = json.indexOf(",\"errorTrace")
        var newJson = json.substring(0, startIdx) + "}"
        val gson = Gson()
        val baseResp = gson.fromJson(newJson, BaseResp::class.java)
        return baseResp
    }
}
fun AutoKeywordResp.toAutoKeyword() = AutoKeyword(
    autoCompletes = autoCompletes ?: listOf()
)
fun SearchShopResp.toShopData() = ShopData(
    pageToken = pageToken ?: "",
    shopQueryResponseList = shopQueryResponseList?.map { it.toShop() }?.toMutableList() ?: mutableListOf<Shop>()
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