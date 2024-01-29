package com.jjbaksa.data.mapper.follower

import com.jjbaksa.data.mapper.user.toFollowContent
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.mapper.user.toUserCount
import com.jjbaksa.data.model.follower.FollowContentResp
import com.jjbaksa.data.model.follower.followRequestRecivedResp
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowResp
import com.jjbaksa.data.model.follower.FollowerListResp
import com.jjbaksa.domain.model.follower.Follow
import com.jjbaksa.domain.model.follower.FollowContent
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.followRequestRecived
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.model.user.User

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

fun followRequestRecivedResp.tofollowRequestRecived() = followRequestRecived(
    content = content?.map { it.toFollowContent() }.orEmpty()
)



