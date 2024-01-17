package com.jjbaksa.domain.model.scrap

import com.google.gson.annotations.SerializedName

data class Scraps(
    @SerializedName("content")
    val content: List<ScrapsContent> = emptyList()
)
