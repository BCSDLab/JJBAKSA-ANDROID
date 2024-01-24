package com.jjbaksa.domain.model.search

import com.google.gson.annotations.SerializedName

data class ShopData(
    @SerializedName("shopQueryResponseList")
    val shopQueryResponseList: MutableList<Shop> = mutableListOf<Shop>(),
    @SerializedName("pageToken")
    val pageToken: String = ""
)
