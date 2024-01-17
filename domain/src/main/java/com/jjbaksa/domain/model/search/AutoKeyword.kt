package com.jjbaksa.domain.model.search

import com.google.gson.annotations.SerializedName

data class AutoKeyword(
    @SerializedName("autoCompletes")
    val autoCompletes: List<String>
)
