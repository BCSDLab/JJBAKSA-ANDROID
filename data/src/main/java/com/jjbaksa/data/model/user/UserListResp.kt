package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.inquiry.InquiryContentResp

data class UserListResp (
    @SerializedName("content")
    val content: List<UserResp>? = emptyList(),
)
