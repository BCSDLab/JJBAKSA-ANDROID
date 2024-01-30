package com.jjbaksa.domain.model.shop


data class ShopsMapsContent(
    val placeId: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val photo: List<String> = emptyList(),
    val totalRating: Int? = 0,
    val ratingCount: Int? = 0,
    val openNow: Boolean? = false,
    val formattedAddress: String? = "",
    val simpleFormattedAddress: String? = ""
)
