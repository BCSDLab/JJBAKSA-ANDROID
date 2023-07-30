package com.jjbaksa.data.model.inquiry.dto

import com.google.gson.annotations.SerializedName

data class InquirySortDTO(
    @SerializedName("empty")
    val empty: Boolean?,
    @SerializedName("sorted")
    val sorted: Boolean?,
    @SerializedName("unsorted")
    val unsorted: Boolean?,
)