package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class WithdrawalReasonReq(
    @SerializedName("reason")
    val reason: String,
    @SerializedName("discomfort")
    val discomfort: String
)
