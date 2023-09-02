package com.jjbaksa.data.model.user

import com.google.gson.annotations.SerializedName

data class UserResp(
    @SerializedName("account")
    var account: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("id")
    var id: Long,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("oauthType")
    var oauthType: String,
    @SerializedName("profileImage")
    var profileImage: UserProfileImageResp,
    @SerializedName("userCountResponse")
    var userCountResponse: UserCountResp,
    @SerializedName("userType")
    var userType: String
)
