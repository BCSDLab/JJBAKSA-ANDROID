package com.jjbaksa.domain.model.scrap

data class ScrapsContent(
    val placeId: String = "",
    val name: String = "",
    val photo: String = "",
    val category: String = "",
    val totalRating: Int = 0,
    val ratingCount: Int = 0,
    val address: String = "",
    val scrapId: Int = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
    val directory: Int = 0,
)
