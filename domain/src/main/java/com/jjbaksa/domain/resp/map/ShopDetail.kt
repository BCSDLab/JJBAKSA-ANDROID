package com.jjbaksa.domain.resp.map

data class ShopDetail(
    val placeId: String = "",
    val category: String = "",
    val lastReviewDate: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val ratingCount: String = "",
    val photos: List<String> = emptyList(),
)
