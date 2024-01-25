package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.FollowCursor
import com.jjbaksa.domain.model.follower.FollowerList
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
    private val _followerListList = SingleLiveEvent<FollowerList>()
    val followerList: SingleLiveEvent<FollowerList> get() = _followerListList
    val followerHasMore = SingleLiveEvent<Boolean>()
    var followState : Enum<FollowCursor> = FollowCursor.UNFOLLOW

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

    fun followRequest(userAccount: String) {
        viewModelScope.launch(ceh) {
            when(followState) {
                FollowCursor.FOLLOW -> {
                    followState = FollowCursor.UNFOLLOW
                    followerUseCase.followRequest(userAccount).collect {
                        it.onSuccess {
                            Log.d("FollowerViewModel", "followRequest: $it")
                        }
                    }
                }
                FollowCursor.UNFOLLOW -> {
                    followState = FollowCursor.FOLLOW
                    followerUseCase.followRequestCancle(userAccount).collect {
                        it.onSuccess {
                            Log.d("FollowerViewModel", "followRequest: $it")
                        }
                    }
                }
            }

        }
    }

}
