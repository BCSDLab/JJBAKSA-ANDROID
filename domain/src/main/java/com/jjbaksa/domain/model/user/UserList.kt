package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class UserList(
    @SerializedName("content")
    val content: List<User> = emptyList()
)
