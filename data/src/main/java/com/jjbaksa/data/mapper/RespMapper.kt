package com.jjbaksa.data.mapper

import com.google.gson.Gson
import com.jjbaksa.data.model.follower.FollowerShopReviewContentDTO
import com.jjbaksa.data.model.follower.FollowerShopReviewResp
import com.jjbaksa.data.model.inquiry.InquiryResp
import com.jjbaksa.data.model.inquiry.dto.InquiryContentDTO
import com.jjbaksa.data.model.inquiry.dto.InquiryImagesDTO
import com.jjbaksa.data.model.map.MapShopResp
import com.jjbaksa.data.model.map.MyReviewContentDTO
import com.jjbaksa.data.model.map.MyReviewResp
import com.jjbaksa.data.model.map.ReviewImagesDTO
import com.jjbaksa.data.model.map.ShopReviewLastDateResp
import com.jjbaksa.data.model.map.ShopReviewResp
import com.jjbaksa.data.model.map.UserReviewResp
import com.jjbaksa.data.model.pin.ShopDetailResp
import com.jjbaksa.data.model.post.PostDetailResp
import com.jjbaksa.data.model.post.PostResp
import com.jjbaksa.data.model.post.dto.PostContentDTO
import com.jjbaksa.data.model.scrap.AddShopScrapResp
import com.jjbaksa.data.model.scrap.ShopScrapResp
import com.jjbaksa.data.model.search.AutoKeywordResp
import com.jjbaksa.data.model.search.SearchShopResp
import com.jjbaksa.data.model.search.ShopResp
import com.jjbaksa.domain.BaseResp
import com.jjbaksa.domain.resp.follower.FollowerShopReview
import com.jjbaksa.domain.resp.follower.FollowerShopReviewContent
import com.jjbaksa.domain.resp.inquiry.InquiryContent
import com.jjbaksa.domain.resp.inquiry.InquiryData
import com.jjbaksa.domain.resp.inquiry.InquiryImages
import com.jjbaksa.domain.resp.map.MapShopContent
import com.jjbaksa.domain.resp.map.MapShopData
import com.jjbaksa.domain.resp.map.ReviewImages
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.resp.map.ShopMyReviewContent
import com.jjbaksa.domain.resp.map.ShopReview
import com.jjbaksa.domain.resp.map.ShopReviewLastDate
import com.jjbaksa.domain.resp.post.Post
import com.jjbaksa.domain.resp.post.PostData
import com.jjbaksa.domain.resp.post.PostDetail
import com.jjbaksa.domain.resp.scrap.AddShopScrap
import com.jjbaksa.domain.resp.scrap.ShopScrap
import com.jjbaksa.domain.resp.search.AutoKeyword
import com.jjbaksa.domain.resp.search.Shop
import com.jjbaksa.domain.resp.search.ShopData
import com.jjbaksa.domain.resp.user.UserReviewInfo

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

fun PostResp.toPostData() = PostData(
    content = content?.map { it.toPost() }.orEmpty()
)

fun PostContentDTO.toPost() = Post(
    id = id ?: 0,
    title = title ?: "",
    createdAt = createdAt ?: ""
)

fun PostDetailResp.toPostDetail() = PostDetail(
    id = id ?: 0,
    title = title ?: "",
    content = content ?: "",
    createdAt = createdAt ?: ""
)

fun List<MapShopResp>?.toMapShopData() = MapShopData(
    mapShopContent = this?.map { it.toMapShopContent() }.orEmpty()
)

fun MapShopResp.toMapShopContent() = MapShopContent(
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

fun ShopScrapResp.toShopScrap() = ShopScrap(
    scrapId = scrapId ?: 0,
    placeId = placeId ?: "",
    category = category ?: "",
    name = name ?: "",
    totalRating = totalRating ?: 0,
    ratingCount = ratingCount ?: 0,
    address = address ?: "",
    photo = photo ?: emptyList(),
)

fun AddShopScrapResp.toAddShopScrap() = AddShopScrap(
    id = id ?: 0,
    createdAt = createdAt ?: 0,
    updatedAt = updatedAt ?: 0,
    directory = directory ?: 0,
    shopId = shopId ?: 0
)

fun ShopReviewResp.toShopReview() = ShopReview(
    id = id ?: 0,
    content = content ?: "",
    rate = rate ?: 0,
    createdAt = createdAt ?: "",
    reviewImages = reviewImages?.map { it.toReviewImages() }.orEmpty()
)

fun ReviewImagesDTO.toReviewImages() = ReviewImages(
    originalName = originalName ?: "",
    imageUrl = imageUrl ?: ""
)

fun MyReviewResp.toShopMyReview() = ShopMyReview(
    content = content?.map { it.toShopMyReviewContent() }.orEmpty()
)

fun MyReviewContentDTO.toShopMyReviewContent() = ShopMyReviewContent(
    id = id ?: 0,
    content = content ?: "",
    rate = rate ?: 0,
    createdAt = createdAt ?: ""
)

fun ShopReviewLastDateResp.toShopReviewLastDate() = ShopReviewLastDate(
    lastDate = lastDate ?: ""
)

fun FollowerShopReviewResp.toFollowerShopReview() = FollowerShopReview(
    content = content?.map { it.toFollowerShopReviewContent() }.orEmpty()
)

fun FollowerShopReviewContentDTO.toFollowerShopReviewContent() = FollowerShopReviewContent(
    id = id ?: 0,
    content = content ?: "",
    rate = rate ?: 0,
    createdAt = createdAt ?: "",
    userReviewResponse = userReviewResponse?.toUserReviewInfo() ?: UserReviewInfo(),
    shopPlaceId = shopPlaceId ?: ""
)

fun UserReviewResp.toUserReviewInfo() = UserReviewInfo(
    id = id ?: 0,
    account = account ?: "",
    nickname = nickname ?: ""
)
