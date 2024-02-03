package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.domain.model.follower.FollowContent
import com.jjbaksa.domain.model.follower.Followers
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

    private val _userList = MutableLiveData<UserList>()
    val userList: MutableLiveData<UserList> get() = _userList

    private val _beRequestedFollowers = SingleLiveEvent<Followers>()
    val beRequestedFollowers: LiveData<Followers> get() = _beRequestedFollowers

    private val _requestFollowers = SingleLiveEvent<Followers>()
    val requestFollowers: LiveData<Followers> get() = _requestFollowers
    private val _recentlyActiveList = SingleLiveEvent<FollowerList>()
    val recentlyActiveList: SingleLiveEvent<FollowerList> get() = _recentlyActiveList

    val followerHasMore = SingleLiveEvent<Boolean>()
    val receivedFollowRequestHasMore = SingleLiveEvent<Boolean>()
    val sendFollowRequestHasMore = SingleLiveEvent<Boolean>()
    val recentlyActiveHasMore = SingleLiveEvent<Boolean>()
    val searchKeyword = SingleLiveEvent<String>()
    val cursor = SingleLiveEvent<UserCursor>()
    fun getFollower(cursor: String?, pageSize: Int) {
        viewModelScope.launch(ceh) {
            followerUseCase.getFollower(cursor, pageSize).collect {
                it.onSuccess {
                    _followerList.value = it
                    _followerList.value?.content?.forEach { item ->
                        item.followedType = "FOLLOWED"
                    }
                    followerHasMore.value = it.content.count() == 20
                }
            }
        }
    }

    fun followerDelete(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followerDelete(userAccount).collect {
                it.onSuccess {
                    (_followerList.value?.content as MutableList)
                        .removeIf { it.account == userAccount }
                }
            }
        }
    }

    fun followRequest(userAccount: String) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequest(userAccount).collect {
                it.onSuccess {
                    (_requestFollowers.value?.content as MutableList).add(
                        FollowContent(
                            follower = it.user,
                            id = it.id,
                            user = it.follower
                        )
                    )
                    _requestFollowers.value?.content?.forEach { item ->
                        item.user.followedType = "REQUEST_SENT"
                    }
                    _requestFollowers.value?.content?.forEach { item ->
                        Log.d("FollowerViewModel", "followRequest: ${item.user.account}")
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
                    (_beRequestedFollowers.value?.content as MutableList).removeIf {
                        it.follower.id == userId
                    }
                    (_followerList.value?.content as MutableList).add(it.follower)
                }
            }
        }
    }

    fun getBeRequestedFollowers(page: Int?, pageSize: Int?) {
        viewModelScope.launch(ceh) {
            followerUseCase.getBeRequestedFollowers(page, pageSize).collect {
                it.onSuccess {
                    _beRequestedFollowers.value = it
                    receivedFollowRequestHasMore.value = it.content.count() == 20
                }
            }
        }
    }

    fun getRequestedFollowers(page: Int?, pageSize: Int?) {
        viewModelScope.launch(ceh) {
            followerUseCase.getRequestedFollowers(page, pageSize).collect {
                it.onSuccess {
                    val list = mutableListOf<FollowContent>()
                    it.content.forEach { item ->
                        list.add(
                            FollowContent(
                                follower = item.user,
                                id = item.id,
                                user = item.follower
                            )
                        )
                    }
                    _requestFollowers.value = Followers(list)
                    sendFollowRequestHasMore.value = it.content.count() == 20
                }
            }
        }
    }

    fun getUserSearch(keyword: String, pageSize: Int?, cursor: Long?) {
        viewModelScope.launch(ceh) {
            userUseCase.getUserSearch(keyword, pageSize, cursor).collect {
                it.onSuccess {
                    _userList.value = it
                }
                searchKeyword.value = keyword

            }
        }
    }

    fun getRecentlyActiveFollowers(pageSize: Int?, cursor: Long?) {
        viewModelScope.launch(ceh) {
            followerUseCase.getRecentlyActiveFollowers(pageSize, cursor).collect {
                it.onSuccess {
                    _recentlyActiveList.value = it
                    recentlyActiveHasMore.value = it.content.count() == 20
                }
            }
        }
    }

    fun followRequestCancel(request_id: Long) {
        viewModelScope.launch(ceh) {
            followerUseCase.followRequestCancel(request_id).collect {
                it.onSuccess {
                    _requestFollowers.value = Followers((_requestFollowers.value?.content as MutableList)
                        .filter { it.user.id != request_id })
                    _requestFollowers.value?.content?.forEach { item ->
                        Log.d("followRequestCancel", "followRequest: ${item.user.account}")
                    }
                }
            }
        }
    }
}
