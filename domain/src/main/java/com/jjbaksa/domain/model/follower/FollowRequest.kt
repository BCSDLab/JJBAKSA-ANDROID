package com.jjbaksa.domain.model.follower

import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.model.user.UserCount

data class FollowRequest (
    val follower: User? = User(),
    val followerCountResp: UserCount? = UserCount(),
    val user: User? = User(),
    val userCountResp: UserCount? = UserCount()
)