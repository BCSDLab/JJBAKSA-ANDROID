package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName

data class UserResp(
    @SerializedName("id")
    val id: Long? = 0,
    @SerializedName("account")
    val account: String? = "",
    @SerializedName("followedType")
    val followedType: String? = "",
    @SerializedName("nickname")
    val nickname: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("profileImage")
    val profileImage: UserProfileImageResp? = UserProfileImageResp(),
    @SerializedName("userCountResponse")
    val userCountResponse: UserCountResp? = UserCountResp(),
    @SerializedName("userType")
    val userType: String? = ""
)
