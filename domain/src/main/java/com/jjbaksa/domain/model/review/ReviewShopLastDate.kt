package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName

data class ReviewShopLastDate(
    @SerializedName("lastDate")
    val lastDate: String = ""
)
