package com.jjbaksa.data.model.search

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.BaseResp

data class SearchShopResp(
    @SerializedName("shopQueryResponseList")
    val shopQueryResponseList: List<ShopResp>? = listOf(),
    @SerializedName("pageToken")
    val pageToken: String? =  ""
): BaseResp()