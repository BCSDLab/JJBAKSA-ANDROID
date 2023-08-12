package com.jjbaksa.data.model.scrap

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AddShopScrapResp(
    @SerializedName("id")
    val id:Int? = 0,
    @SerializedName("createdAt")
    val createdAt:Long? = 0,
    @SerializedName("updatedAt")
    val updatedAt:Long? = 0,
    @SerializedName("directory")
    val directory:Int? = 0,
    @SerializedName("shopId")
    val shopId:Int? = 0
)
