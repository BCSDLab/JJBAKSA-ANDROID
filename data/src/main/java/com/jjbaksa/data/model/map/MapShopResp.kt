package com.jjbaksa.data.model.map

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.ErrorResp

data class MapShopResp(
    @SerializedName("placeId")
    val placeId: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("geometry")
    val geometry: GeometryDTO,
    @SerializedName("photo")
    val photo: String? = ""
) : ErrorResp()
