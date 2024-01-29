package com.jjbaksa.data.model.shop

import com.google.gson.annotations.SerializedName

data class PeriodResp(
    @SerializedName("closeTime")
    val close: TimeResp? = null,
    @SerializedName("openTime")
    val open: TimeResp? = null
)
