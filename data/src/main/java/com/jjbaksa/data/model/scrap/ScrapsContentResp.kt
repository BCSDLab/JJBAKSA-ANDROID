package com.jjbaksa.data.model.scrap

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.pin.RateDto

data class ScrapsContentResp(
    @SerializedName("placeId")
    val placeId: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("photo")
    val photo: String? = "",
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("rate")
    val rate: RateDto? = RateDto(),
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("scrapId")
    val scrapId: Int? = 0,
    @SerializedName("createdAt")
    val createdAt: Long? = 0,
    @SerializedName("updatedAt")
    val updatedAt: Long? = 0,
    @SerializedName("directory")
    val directory: Int? = 0,
)
