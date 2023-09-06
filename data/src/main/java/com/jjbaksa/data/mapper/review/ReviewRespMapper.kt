package com.jjbaksa.data.mapper.review

import com.jjbaksa.data.mapper.user.toUserProfileImage
import com.jjbaksa.data.model.follower.FollowerReviewShopsContentDTO
import com.jjbaksa.data.model.follower.FollowerReviewShopsResp
import com.jjbaksa.data.model.review.ReviewShopLastDateResp
import com.jjbaksa.data.model.review.MyReviewShopsContentResp
import com.jjbaksa.data.model.review.MyReviewShopsResp
import com.jjbaksa.data.model.review.ReviewImageResp
import com.jjbaksa.data.model.review.ReviewShopContentDTO
import com.jjbaksa.data.model.review.ReviewShopDetailResp
import com.jjbaksa.data.model.review.ReviewShopResp
import com.jjbaksa.data.model.review.UserReviewResp
import com.jjbaksa.domain.model.user.UserProfileImage
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.FollowerReviewShopsContent
import com.jjbaksa.domain.model.review.ReviewShopLastDate
import com.jjbaksa.domain.model.review.MyReviewShops
import com.jjbaksa.domain.model.review.MyReviewShopsContent
import com.jjbaksa.domain.model.review.ReviewImages
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.model.review.ReviewShopContent
import com.jjbaksa.domain.model.review.ReviewShopDetail
import com.jjbaksa.domain.model.review.UserReview

fun ReviewShopResp.toReviewShop() = ReviewShop(
    content = content?.map { it.toReviewShopContent() }.orEmpty()
)

fun ReviewShopContentDTO.toReviewShopContent() = ReviewShopContent(
    shopId = shopId ?: 0,
    placeId = placeId ?: "",
    name = name ?: "",
    category = category ?: "",
    scrap = scrap ?: 0
)

fun FollowerReviewShopsResp.toFollowerShopReview() = FollowerReviewShops(
    content = content?.map { it.toFollowerShopReviewContent() }.orEmpty()
)

fun FollowerReviewShopsContentDTO.toFollowerShopReviewContent() = FollowerReviewShopsContent(
    id = id ?: 0,
    content = content ?: "",
    rate = rate ?: 0,
    createdAt = createdAt ?: "",
    userReviewResponse = userReviewResponse?.toUserReview() ?: UserReview(),
    shopPlaceId = shopPlaceId ?: ""
)

fun UserReviewResp.toUserReview() = UserReview(
    id = id ?: 0,
    account = account ?: "",
    nickname = nickname ?: "",
    profileImage = profileImage?.toUserProfileImage() ?: UserProfileImage()
)

fun MyReviewShopsResp.toMyReviewShops() = MyReviewShops(
    content = content?.map { it.toMyReviewShopsContent() }.orEmpty()
)

fun MyReviewShopsContentResp.toMyReviewShopsContent() = MyReviewShopsContent(
    id = id ?: 0,
    content = content ?: "",
    rate = rate ?: 0,
    createdAt = createdAt ?: ""
)

fun ReviewShopLastDateResp.toReviewShopLastDate() = ReviewShopLastDate(
    lastDate = lastDate ?: ""
)

fun ReviewShopDetailResp.toReviewShopDetail() = ReviewShopDetail(
    id = id ?: 0,
    content = content ?: "",
    rate = rate ?: 0,
    createdAt = createdAt ?: "",
    reviewImages = reviewImages?.map { it.toReviewImages() }.orEmpty()
)

fun ReviewImageResp.toReviewImages() = ReviewImages(
    originalName = originalName ?: "",
    imageUrl = imageUrl ?: ""
)
