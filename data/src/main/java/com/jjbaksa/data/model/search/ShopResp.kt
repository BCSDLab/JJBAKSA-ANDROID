package com.jjbaksa.data.model.search

import com.google.gson.annotations.SerializedName

data class ShopResp(
    @SerializedName("category") val category: String?,
    @SerializedName("dist") val dist: Double?,
    @SerializedName("formattedAddress") val formattedAddress: String?,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lng") val lng: Double?,
    @SerializedName("name") val name: String?,
    @SerializedName("openNow") val openNow: Boolean?,
    @SerializedName("photoToken") val photoToken: String?,
    @SerializedName("placeId") val placeId: String?,
    @SerializedName("ratingCount") val ratingCount: Int?,
    @SerializedName("totalRating") val totalRating: Int?
)
