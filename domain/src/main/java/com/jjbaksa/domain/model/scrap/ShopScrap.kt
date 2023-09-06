package com.jjbaksa.domain.model.scrap

data class ShopScrap(
    val scrapId: Int = 0,
    val placeId: String = "",
    val category: String = "",
    val name: String = "",
    val totalRating: Int = 0,
    val ratingCount: Int = 0,
    val address: String = "",
    val photo: List<String> = emptyList(),
)
