package com.jjbaksa.domain.resp.review

data class ReviewShopContent(
    val shopId: Int = 0,
    val placeId: String = "",
    val name: String = "",
    val category: String = "",
    val scrap: Int = 0
)
