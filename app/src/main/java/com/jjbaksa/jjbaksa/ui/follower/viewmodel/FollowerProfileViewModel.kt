package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.usecase.follower.FollowerUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerProfileViewModel @Inject constructor(
    private val followerUseCase: FollowerUseCase
) : BaseViewModel() {

    var fid = 0L

    private val _followerReviewCount = SingleLiveEvent<Int>()
    val totalReviewCount: SingleLiveEvent<Int> get() = _followerReviewCount

    private val _reviewedShops = SingleLiveEvent<ReviewShop>()
    val reviewedShops: SingleLiveEvent<ReviewShop> get() = _reviewedShops

    fun getFollowerReviewCount() {
        viewModelScope.launch(ceh) {
            followerUseCase.getFollowerReviewCount(fid).collect {
                it.onSuccess {
                    _followerReviewCount.value = it
                }
            }
        }
    }

    fun getReviewedShops() {
        viewModelScope.launch(ceh) {
            followerUseCase.getReviewedShops(fid).collect {
                it.onSuccess {
                    _reviewedShops.value = it
                }
            }
        }
    }

    fun getShopReview(id: Long, placeId: String, onComplete: (FollowerReviewShops) -> Unit) {
        viewModelScope.launch(ceh) {
            followerUseCase.getShopReview(id, placeId).collect {
                it.onSuccess {
                    onComplete(it)
                }
            }
        }
    }
}
