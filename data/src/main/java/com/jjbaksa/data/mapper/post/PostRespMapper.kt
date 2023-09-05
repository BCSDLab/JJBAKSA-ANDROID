package com.jjbaksa.data.mapper.post

import com.jjbaksa.data.model.post.PostDetailResp
import com.jjbaksa.data.model.post.PostResp
import com.jjbaksa.data.model.post.dto.PostContentResp
import com.jjbaksa.domain.model.post.PostContent
import com.jjbaksa.domain.model.post.Post
import com.jjbaksa.domain.model.post.PostDetail

fun PostResp.toPost() = Post(
    content = content?.map { it.toPostContent() }.orEmpty()
)

fun PostContentResp.toPostContent() = PostContent(
    id = id ?: 0,
    title = title ?: "",
    createdAt = createdAt ?: ""
)

fun PostDetailResp.toPostDetail() = PostDetail(
    id = id ?: 0,
    title = title ?: "",
    content = content ?: "",
    createdAt = createdAt ?: ""
)
