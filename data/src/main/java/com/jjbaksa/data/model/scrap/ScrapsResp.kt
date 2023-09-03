package com.jjbaksa.data.model.scrap

import com.google.gson.annotations.SerializedName

data class ScrapsResp(
    @SerializedName("content")
    val content: List<ScrapsContentResp>? = emptyList()
)
