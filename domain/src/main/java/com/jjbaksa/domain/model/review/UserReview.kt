package com.jjbaksa.domain.model.review

import com.jjbaksa.domain.model.user.UserProfileImage

data class UserReview(
    val id: Int = 0,
    val account: String = "",
    val nickname: String = "",
    val profileImage: UserProfileImage = UserProfileImage()
)
