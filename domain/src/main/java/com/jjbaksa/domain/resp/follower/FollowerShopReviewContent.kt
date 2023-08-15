package com.jjbaksa.domain.resp.follower

import com.jjbaksa.domain.resp.user.UserReviewInfo

data class FollowerShopReviewContent(
    val id: Int = 0,
    val content: String = "",
    val rate: Int = 0,
    val createdAt: String = "",
    val userReviewResponse: UserReviewInfo = UserReviewInfo(),
    val shopPlaceId: String = ""
)
