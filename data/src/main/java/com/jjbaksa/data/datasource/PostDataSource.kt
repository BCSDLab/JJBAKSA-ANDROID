package com.jjbaksa.data.datasource

import com.jjbaksa.data.model.post.PostDetailResp
import com.jjbaksa.data.model.post.PostResp
import retrofit2.Response

interface PostDataSource {
    suspend fun getPost(idCursor: String, dateCursor: String, size: Int): Response<PostResp>
    suspend fun getPostDetail(postId: Int): Response<PostDetailResp>
}
