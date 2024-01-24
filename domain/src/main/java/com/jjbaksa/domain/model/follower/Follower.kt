package com.jjbaksa.domain.model.follower

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.model.inquiry.InquiryContent
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.model.user.UserInfo

data class Follower(
    @SerializedName("content")
    val content: List<User> = emptyList()
)
