package com.jjbaksa.data.model.search

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.ErrorResp

data class TrendResp(
    @SerializedName("trendings")
    val trendings: List<String>
) : ErrorResp()
