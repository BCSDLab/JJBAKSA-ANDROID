package com.jjbaksa.domain.model.user

data class User(
    val id: Long = 0,
    val account: String = "",
    val nickname: String = "",
    val email: String = "",
    val profileImage: UserProfileImage = UserProfileImage(),
    val userCountResponse: UserCount = UserCount(),
    val userType: String = ""
)
