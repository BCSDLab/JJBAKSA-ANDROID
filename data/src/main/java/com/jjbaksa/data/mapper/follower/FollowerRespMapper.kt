package com.jjbaksa.data.mapper.follower

import com.jjbaksa.data.mapper.user.toFollowContent
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.model.follower.FollowersListResp
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowerListResp
import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.Followers
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
    follower = follower?.toUser()
)

fun FollowersListResp.tofollowRequestRecived() = Followers(
    content = content?.map { it.toFollowContent() }.orEmpty()
)
