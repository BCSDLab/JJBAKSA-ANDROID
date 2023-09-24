package com.jjbaksa.data.model.pin

import com.google.gson.annotations.SerializedName

data class PeriodResp(
    @SerializedName("close")
    val close: CloseResp?,
    @SerializedName("open")
    val open: OpenResp?
)