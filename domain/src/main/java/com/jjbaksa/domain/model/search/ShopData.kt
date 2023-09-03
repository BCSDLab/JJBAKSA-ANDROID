package com.jjbaksa.domain.model.search

data class ShopData(
    val shopQueryResponseList: MutableList<Shop> = mutableListOf<Shop>(),
    val pageToken: String = ""
)
