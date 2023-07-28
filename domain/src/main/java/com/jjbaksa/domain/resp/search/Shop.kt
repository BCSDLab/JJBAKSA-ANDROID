package com.jjbaksa.domain.resp.search

data class Shop(
    var dist: Double = 0.0,
    var category: String = "",
    val formattedAddress: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val name: String = "",
    val openNow: Boolean = false,
    val photoToken: String = "",
    val placeId: String = "",
    val ratingCount: Int = 0,
    val totalRating: Int = 0,
    var type: Int = 1
)
