package com.jjbaksa.domain.model.review

data class ReviewShopDetail(
    val id: Int? = 0,
    val content: String? = "",
    val rate: Int? = 0,
    val createdAt: String? = "",
    val reviewImages: List<ReviewImages>? = emptyList(),
)
