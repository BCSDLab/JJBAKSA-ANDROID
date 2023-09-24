package com.jjbaksa.data.model.pin

import com.google.gson.annotations.SerializedName

data class CloseResp(
    @SerializedName("day")
    val day: Int? = 0,
    @SerializedName("time")
    val time: Int? = 0
)