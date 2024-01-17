package com.jjbaksa.domain.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("account")
    val account: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("profileImage")
    val profileImage: UserProfileImage = UserProfileImage(),
    @SerializedName("userCountResponse")
    val userCountResponse: UserCount = UserCount(),
    @SerializedName("userType")
    val userType: String = ""
)
