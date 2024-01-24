package com.jjbaksa.data.mapper.follower

import com.jjbaksa.data.mapper.inquiry.toInquiryContent
import com.jjbaksa.data.mapper.user.toUser
import com.jjbaksa.data.model.follower.FollowerResp
import com.jjbaksa.domain.model.follower.Follower

fun FollowerResp.toFollower() = Follower(
    content = content?.map { it.toUser() }.orEmpty()
)
