package com.jjbaksa.data.mapper.follower

import com.jjbaksa.data.mapper.inquiry.toInquiryContent
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.mapper.user.toUserCount
import com.jjbaksa.data.model.follower.FollowRequestResp
import com.jjbaksa.data.model.follower.FollowerResp
import com.jjbaksa.domain.model.follower.FollowRequest
import com.jjbaksa.domain.model.follower.Follower

fun FollowerResp.toFollower() = Follower(
    content = content?.map { it.toUser() }.orEmpty()
)

fun FollowRequestResp.toFollowRequest() = FollowRequest(
    follower = follower?.toUser(),
    followerCountResp = followerCountResp?.toUserCount(),
    user = user?.toUser(),
    userCountResp = userCountResp?.toUserCount()
)
