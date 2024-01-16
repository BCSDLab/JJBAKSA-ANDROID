package com.jjbaksa.domain.model.shop

import com.google.gson.annotations.SerializedName

data class Period(
    @SerializedName("close")
    val close: Close = Close(),
    @SerializedName("open")
    val open: Open = Open()
)
