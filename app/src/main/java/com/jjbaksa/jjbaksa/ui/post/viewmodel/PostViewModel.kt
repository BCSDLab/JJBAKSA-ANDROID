package com.jjbaksa.jjbaksa.ui.post.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.resp.post.PostData
import com.jjbaksa.domain.resp.post.PostDetail
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
    private val _post = SingleLiveEvent<PostData>()
    val post: SingleLiveEvent<PostData> get() = _post

    private val _postDetail = SingleLiveEvent<PostDetail>()
    val postDetail: SingleLiveEvent<PostDetail> get() = _postDetail

    fun getPost(idCursor: String, dateCursor: String, size: Int) {
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
