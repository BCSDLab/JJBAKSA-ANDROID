package com.jjbaksa.data.mapper.follower

import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.mapper.user.toUserCount
import com.jjbaksa.data.model.follower.FollowRequestCheckResp
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowerListResp
import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.FollowRequestCheck
import com.jjbaksa.domain.model.follower.FollowerList

fun FollowerListResp.toFollower() = FollowerList(
    content = content?.map { it.toUser() }.orEmpty()
)

fun FollowRequestResp.toFollowRequest() = FollowRequest(
    follower = follower?.toUser(),
    user = user?.toUser(),
    id = id
)

fun FollowResp.toFollow() = Follow(
    follower = follower?.toUser(),
    followerCountResp = followerCountResp?.toUserCount()
)

fun FollowRequestCheckResp.toFollowRequestCheck() = FollowRequestCheck(
    page = page,
    pageSize = pageSize,
)