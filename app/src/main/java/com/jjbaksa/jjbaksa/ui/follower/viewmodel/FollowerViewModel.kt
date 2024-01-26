package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.FollowCursor
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.model.user.User
import com.jjbaksa.domain.usecase.follower.FollowerUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val followerUseCase: FollowerUseCase
) : BaseViewModel() {
    private val _followerList = SingleLiveEvent<FollowerList>()
    val followerList: SingleLiveEvent<FollowerList> get() = _followerList
    val followerHasMore = SingleLiveEvent<Boolean>()
    val unfollowedUsers = mutableListOf<String>()

    fun getFollower(cursor: String?, pageSize: Int) {
        viewModelScope.launch(ceh) {
            followerUseCase.getFollower(cursor, pageSize).collect {
                it.onSuccess {
                    _followerList.value = it
                    followerHasMore.value = it.content.count() == 10
                }

            }
        }
    }

    fun followRequest(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequest(userAccount).collect {
                it.onSuccess {
                    if(unfollowedUsers.contains(userAccount)) {
                        unfollowedUsers.remove(userAccount)
                    }
                }
            }
        }
    }
    
    fun followerDelete(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followerDelete(userAccount).collect {
                it.onSuccess {
                    unfollowedUsers.add(userAccount)
                }
            }
        }
    }
}

