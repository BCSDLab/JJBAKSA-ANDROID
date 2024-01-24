package com.jjbaksa.domain.model.scrap

import com.google.gson.annotations.SerializedName

data class AddShopScrap(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("createdAt")
    val createdAt: Long = 0,
    @SerializedName("updatedAt")
    val updatedAt: Long = 0,
    @SerializedName("directory")
    val directory: Int = 0,
    @SerializedName("shopId")
    val shopId: Int = 0
)
