package com.jjbaksa.data.model.shop

import com.google.gson.annotations.SerializedName

data class TimeResp(
    @SerializedName("hour")
    val hour: Int?,
    @SerializedName("minute")
    val minute: Int?
)