package com.jjbaksa.jjbaksa.ui.post.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.resp.post.PostData
import com.jjbaksa.domain.usecase.post.GetPostUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
): BaseViewModel() {
    private val _post = SingleLiveEvent<PostData>()
    val post: SingleLiveEvent<PostData> get() = _post

    fun getPost(idCursor: String, dateCursor: String, size: Int) {
        viewModelScope.launch(ceh) {
            getPostUseCase.invoke(idCursor, dateCursor, size)
                .collect {
                    it.onSuccess { _post.value = it }
                }
        }
    }
}