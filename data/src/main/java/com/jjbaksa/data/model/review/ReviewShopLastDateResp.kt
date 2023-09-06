package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName

data class ReviewShopLastDateResp(
    @SerializedName("lastDate")
    val lastDate: String? = ""
)
