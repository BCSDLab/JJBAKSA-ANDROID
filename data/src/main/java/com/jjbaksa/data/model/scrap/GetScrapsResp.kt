package com.jjbaksa.data.model.scrap

import com.google.gson.annotations.SerializedName

data class GetScrapsResp(
    @SerializedName("content")
    val content: List<UserScrapsShopResp>? = emptyList()
)
