package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.domain.model.follower.followRequestRecived
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

    private val _UserList = MutableLiveData<UserList>()
    val userList: MutableLiveData<UserList> get() = _UserList
    private val _receivedFollowRequestList = SingleLiveEvent<followRequestRecived>()
    val receivedFollowRequestList: LiveData<followRequestRecived> get() = _receivedFollowRequestList
    private val _sendFollowRequestList = SingleLiveEvent<followRequestRecived>()
    val sendFollowRequestList: LiveData<followRequestRecived> get() = _sendFollowRequestList



    val followerHasMore = SingleLiveEvent<Boolean>()
    val receivedFollowRequestHasMore = SingleLiveEvent<Boolean>()
    val sendFollowRequestHasMore = SingleLiveEvent<Boolean>()
    val unfollowedUsers = mutableListOf<String>()
    val searchKeyword = SingleLiveEvent<String>()

    val cursor = SingleLiveEvent<UserCursor>()
    fun getFollower(cursor: String?, pageSize: Int) {
        viewModelScope.launch(ceh) {
            followerUseCase.getFollower(cursor, pageSize).collect {
                it.onSuccess {
                    _followerList.value = it
                    followerHasMore.value = it.content.count() == 20
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
                        unfollowedUsers.remove(userAccount)
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
                    unfollowedUsers.remove(userId.toString())
                }
            }
        }
    }


    fun followRequestReceived(page: Int?, pageSize: Int?) {

        viewModelScope.launch(ceh) {
            followerUseCase.followRequestRecived(page, pageSize).collect {
                it.onSuccess {
                    _receivedFollowRequestList.value = it
                    receivedFollowRequestHasMore.value = it.content.count() == 20
                }
            }
        }
    }


    fun followRequestSend(page: Int?, pageSize: Int?) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequestSend(page, pageSize).collect {
                it.onSuccess {
                    _sendFollowRequestList.value = it
                    sendFollowRequestHasMore.value = it.content.count() == 20
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

