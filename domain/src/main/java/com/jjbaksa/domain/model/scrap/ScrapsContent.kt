package com.jjbaksa.domain.model.scrap

import com.google.gson.annotations.SerializedName

data class ScrapsContent(
    @SerializedName("placeId")
    val placeId: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("photo")
    val photo: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("totalRating")
    val totalRating: Int = 0,
    @SerializedName("ratingCount")
    val ratingCount: Int = 0,
    @SerializedName("address")
    val address: String = "",
    @SerializedName("scrapId")
    val scrapId: Int = 0,
    @SerializedName("createdAt")
    val createdAt: Long = 0,
    @SerializedName("updatedAt")
    val updatedAt: Long = 0,
    @SerializedName("directory")
    val directory: Int = 0,
)
