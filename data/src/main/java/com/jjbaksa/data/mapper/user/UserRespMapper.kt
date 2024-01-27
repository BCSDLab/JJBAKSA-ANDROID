package com.jjbaksa.data.mapper.user

import com.jjbaksa.data.model.follower.FollowContentResp
import com.jjbaksa.data.model.follower.FollowRequestCheckResp
import com.jjbaksa.data.model.user.LoginResp
import com.jjbaksa.data.model.user.UserCountResp
import com.jjbaksa.data.model.user.UserProfileImageResp
import com.jjbaksa.data.model.user.UserResp
import com.jjbaksa.domain.model.follower.FollowContent
import com.jjbaksa.domain.model.follower.FollowRequestCheck
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.model.user.UserCount
import com.jjbaksa.domain.model.user.UserProfileImage

fun UserResp.toUser() = User(
    id = id ?: 0,
    account = account ?: "",
    nickname = nickname ?: "",
    email = email ?: "",
    profileImage = profileImage?.toUserProfileImage() ?: UserProfileImage(),
    userCountResponse = userCountResponse?.toUserCount() ?: UserCount(),
    userType = userType ?: ""
)

fun UserProfileImageResp.toUserProfileImage() = UserProfileImage(
    id = id ?: 0,
    originalName = originalName ?: "",
    path = path ?: "",
    url = url ?: ""
)

fun UserCountResp.toUserCount() = UserCount(
    id = id ?: 0,
    friendCount = friendCount ?: 0,
    reviewCount = reviewCount ?: 0
)

fun LoginResp.toLoginResult() = Login(
    accessToken = accessToken ?: "",
    refreshToken = refreshToken ?: "",
    errorMessage = errorMessage,
    isSuccess = true
)
fun FollowContentResp.toFollowContent() = FollowContent(
    follower = follower.toUser(),
    id = id ?: 0,
    user = user.toUser()
)




