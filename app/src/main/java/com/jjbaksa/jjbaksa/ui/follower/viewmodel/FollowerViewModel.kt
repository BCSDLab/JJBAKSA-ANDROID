package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.FollowCursor
import com.jjbaksa.domain.model.follower.FollowRequestCheck
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

    private val _followRequestList = MutableLiveData<FollowRequestCheck>()
    val followRequestList : LiveData<FollowRequestCheck> get() = _followRequestList

    val followRequestHasMore= SingleLiveEvent<Boolean>()
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

    fun followerDelete(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followerDelete(userAccount).collect {
                it.onSuccess {
                    unfollowedUsers.add(userAccount)
                }
            }
        }
    }


    fun followRequest(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequest(userAccount).collect {
                it.onSuccess {
                    if(unfollowedUsers.contains(userAccount)) {
                       // unfollowedUsers.remove(userAccount)
                    }
                }
            }
        }
    }

    fun followRequestDelete(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followerDelete(userAccount).collect {
                it.onSuccess {
                   // unfollowedUsers.add(userAccount)
                }
            }
        }
    }

    fun followRequestAccept(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequestAccept(userAccount).collect {
                it.onSuccess {
                    if(unfollowedUsers.contains(userAccount)) {
                        unfollowedUsers.remove(userAccount)
                    }
                }
            }
        }
    }


    fun followRequestCheck(page: Int?, pageSize: Int?) {

        viewModelScope.launch(ceh) {
            followerUseCase.followRequestCheck(page, pageSize).collect {
                it.onSuccess {
                    _followRequestList.value = it
                    followRequestHasMore.value = it.content.count() == 10
                }
            }
        }
    }

}

