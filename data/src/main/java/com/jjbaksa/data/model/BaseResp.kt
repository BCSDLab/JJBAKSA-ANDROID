package com.jjbaksa.domain

import com.google.gson.annotations.SerializedName

open class BaseResp {
    @SerializedName("errorMessage")
    val errorMessage: String = ""

    @SerializedName("code")
    val code: Int = 0
}
