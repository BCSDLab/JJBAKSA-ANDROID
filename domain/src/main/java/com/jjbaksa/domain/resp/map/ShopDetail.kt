package com.jjbaksa.domain.resp.map

data class ShopDetail(
    val shopId: Int = 0,
    val placeId: String = "",
    val category: String = "",
    val name: String = "",
    val totalRating: Int = 0,
    val ratingCount: Int = 0,
    val scrap: Int = 0,
    val photos: List<String> = emptyList(),
)
