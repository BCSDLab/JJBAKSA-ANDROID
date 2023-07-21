package com.jjbaksa.domain.resp.search

data class ShopData(
    val shopQueryResponseList: MutableList<Shop> = mutableListOf<Shop>(),
    val pageToken: String = ""
)
