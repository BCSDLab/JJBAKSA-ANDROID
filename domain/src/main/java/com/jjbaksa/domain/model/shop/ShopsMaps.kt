package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class ShopsMaps(
    @SerializedName("shopsMapsContent")
    val shopsMapsContent: List<ShopsMapsContent> = emptyList()
)
