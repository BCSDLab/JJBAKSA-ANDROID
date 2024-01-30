package com.jjbaksa.data.model.shop

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.CoordinateDto
import com.jjbaksa.data.model.pin.RateDto
import com.jjbaksa.domain.ErrorResp

data class ShopsMapsResp(
    @SerializedName("placeId")
    val placeId: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("coordinate")
    val coordinate: CoordinateDto,
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("photos")
    val photo: List<String>? = emptyList(),
    @SerializedName("rate")
    val rate: RateDto? = RateDto(),
    @SerializedName("openNow")
    val openNow: Boolean? = false,
    @SerializedName("formattedAddress")
    val formattedAddress: String? = "",
    @SerializedName("simpleFormattedAddress")
    val simpleFormattedAddress: String? = ""
) : ErrorResp()
