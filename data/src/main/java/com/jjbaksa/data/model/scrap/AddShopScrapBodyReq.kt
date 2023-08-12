package com.jjbaksa.data.model.scrap

import com.google.gson.annotations.SerializedName

data class AddShopScrapBodyReq(
    @SerializedName("directoryId")
    val directoryId: Int? = 0,
    @SerializedName("placeId")
    val placeId: String? = ""
)
