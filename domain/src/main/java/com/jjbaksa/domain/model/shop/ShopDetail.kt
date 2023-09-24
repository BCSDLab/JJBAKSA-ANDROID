package com.jjbaksa.domain.model.shop

data class ShopDetail(
    val shopId: Int = 0,
    val placeId: String = "",
    val category: String = "",
    val name: String = "",
    val totalRating: Int = 0,
    val ratingCount: Int = 0,
    val scrap: Int = 0,
    val photos: List<String> = emptyList(),
    val formattedAddress: String = "",
    val formattedPhoneNumber: String = "",
    val openNow: Boolean = false,
    val period: List<Period> = emptyList(),
)
