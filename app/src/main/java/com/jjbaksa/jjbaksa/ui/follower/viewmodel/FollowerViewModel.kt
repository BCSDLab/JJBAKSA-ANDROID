package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.domain.model.follower.FollowRequestCheck
import com.jjbaksa.domain.model.follower.FollowerList
import com.jjbaksa.domain.model.user.UserList
import com.jjbaksa.domain.usecase.follower.FollowerUseCase
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val followerUseCase: FollowerUseCase,
    private val userUseCase: UserUseCase
) : BaseViewModel() {
    private val _followerList = SingleLiveEvent<FollowerList>()
    val followerList: SingleLiveEvent<FollowerList> get() = _followerList

    private val _UserList = SingleLiveEvent<UserList>()
    val userList: LiveData<UserList> get() = _UserList
    private val _followRequestList = SingleLiveEvent<FollowRequestCheck>()
    val followRequestList: LiveData<FollowRequestCheck> get() = _followRequestList

    val followerHasMore = SingleLiveEvent<Boolean>()
    val followRequestHasMore = SingleLiveEvent<Boolean>()
    val unfollowedUsers = mutableListOf<String>()
    val  searchKeyword = SingleLiveEvent<String>()

    val curser = SingleLiveEvent<UserCursor>()
    fun getFollower(cursor: String?, pageSize: Int) {
        viewModelScope.launch(ceh) {

            followerUseCase.getFollower(cursor, pageSize).collect {
                it.onSuccess {
                    _followerList.value = it
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
                    if (unfollowedUsers.contains(userAccount)) {
                    }
                }
            }
        }
    }

    fun followRequestReject(userId: Long) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequestReject(userId).collect {
                it.onSuccess {
                }
            }
        }
    }

    fun followRequestAccept(userId: Long) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequestAccept(userId).collect {
                it.onSuccess {
                }
            }
        }
    }


    fun followRequestCheck(page: Int?, pageSize: Int?) {

        viewModelScope.launch(ceh) {
            followerUseCase.followRequestCheck(page, pageSize).collect {
                it.onSuccess {
                    _followRequestList.value = it

                }
            }
        }
    }


    fun followRequestSend(page: Int?, pageSize: Int?) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequestSend(page, pageSize).collect {
                it.onSuccess {
                    _followRequestList.value = it
                }
            }
        }
    }

    fun getUserSearch(keyword: String, pageSize: Int?, cursor: Long?) {
        viewModelScope.launch(ceh) {
            userUseCase.getUserSearch(keyword, pageSize, cursor).collect {
                it.onSuccess {
                    _UserList.value = it
                }

                    searchKeyword.value = keyword
            }
        }
    }
}

