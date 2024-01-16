package com.jjbaksa.domain.model.review

import com.google.gson.annotations.SerializedName
import com.jjbaksa.domain.model.user.UserProfileImage

data class UserReview(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("account")
    val account: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("profileImage")
    val profileImage: UserProfileImage = UserProfileImage()
)
