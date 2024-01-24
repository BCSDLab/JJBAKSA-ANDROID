package com.jjbaksa.data.model.search

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.ErrorResp

data class AutoKeywordResp(
    @SerializedName("autoCompletes")
    val autoCompletes: List<String>? = null
) : ErrorResp()
