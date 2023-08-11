package com.jjbaksa.data.model.map

import com.google.gson.annotations.SerializedName

data class ReviewImagesDTO(
    @SerializedName("originalName")
    val originalName: String? = "",
    @SerializedName("imageUrl")
    val imageUrl: String? = ""
)
