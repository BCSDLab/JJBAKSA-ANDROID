package com.jjbaksa.data.model.review

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.user.UserProfileImageResp

data class UserReviewResp(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("account")
    val account: String? = "",
    @SerializedName("nickname")
    val nickname: String? = "",
    @SerializedName("profileImage")
    val profileImage: UserProfileImageResp? = UserProfileImageResp()
)
