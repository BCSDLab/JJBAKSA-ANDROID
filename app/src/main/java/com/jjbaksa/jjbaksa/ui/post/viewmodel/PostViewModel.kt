package com.jjbaksa.jjbaksa.ui.post.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.post.Post
import com.jjbaksa.domain.model.post.PostDetail
import com.jjbaksa.domain.usecase.post.GetPostUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
) : BaseViewModel() {
    private val _post = SingleLiveEvent<Post>()
    val post: SingleLiveEvent<Post> get() = _post

    private val _postDetail = SingleLiveEvent<PostDetail>()
    val postDetail: SingleLiveEvent<PostDetail> get() = _postDetail

    fun getPost(idCursor: Int?, dateCursor: String?, size: Int) {
        viewModelScope.launch(ceh) {
            getPostUseCase.invoke(idCursor, dateCursor, size)
                .collect {
                    it.onSuccess { _post.value = it }
                }
        }
    }

    fun getPostDetail(postId: Int) {
        viewModelScope.launch(ceh) {
            getPostUseCase.postDetail(postId)
                .collect {
                    it.onSuccess { _postDetail.value = it }
                }
        }
    }
}
