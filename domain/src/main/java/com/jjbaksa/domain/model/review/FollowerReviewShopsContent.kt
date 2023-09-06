package com.jjbaksa.domain.model.review

data class FollowerReviewShopsContent(
    val id: Int = 0,
    val content: String = "",
    val rate: Int = 0,
    val createdAt: String = "",
    val userReviewResponse: UserReview = UserReview(),
    val shopPlaceId: String = ""
)
