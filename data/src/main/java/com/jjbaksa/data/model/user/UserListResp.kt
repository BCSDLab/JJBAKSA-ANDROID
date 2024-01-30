package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName

data class UserListResp(
    @SerializedName("content")
    val content: List<UserResp>? = emptyList(),
)
