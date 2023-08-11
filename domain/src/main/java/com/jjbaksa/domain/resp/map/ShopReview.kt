package com.jjbaksa.domain.resp.map

data class ShopReview (
    val id: Int? = 0,
    val content: String? = "",
    val rate: Int? = 0,
    val createdAt: String? = "",
    val reviewImages: List<ReviewImages>? = emptyList(),
)