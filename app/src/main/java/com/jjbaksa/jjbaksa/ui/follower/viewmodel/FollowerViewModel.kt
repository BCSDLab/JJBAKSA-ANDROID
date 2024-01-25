package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.domain.model.follower.Follower
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.usecase.follower.FollowerUseCase
import com.jjbaksa.domain.usecase.inquiry.InquiryUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val followerUseCase: FollowerUseCase
) : BaseViewModel() {
    private val _followerList = SingleLiveEvent<Follower>()
    val followerList: SingleLiveEvent<Follower> get() = _followerList
    val followerHasMore = SingleLiveEvent<Boolean>()

    fun getFollower(cursor: String?, pageSize: Int) {
        viewModelScope.launch(ceh) {
            followerUseCase.getFollower(cursor, pageSize).collect {
                it.onSuccess {

                    followerList.value = it
                    followerHasMore.value = it.content.count() == 10
                }

            }
        }
    }

    fun followRequest(userAccount: String?) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequest(userAccount).collect {
                it.onSuccess {
                    Log.d("followUser", "success")
                }
            }
        }
    }

}
