package com.jjbaksa.domain.resp.user

import com.jjbaksa.domain.model.user.UserProfileImage

data class UserReviewInfo(
    val id: Int = 0,
    val account: String = "",
    val nickname: String = "",
    val profileImage: UserProfileImage = UserProfileImage()
)
