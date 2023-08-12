package com.jjbaksa.jjbaksa.ui.pin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.FriendReviewCursor
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.resp.follower.FollowerShopReview
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.resp.map.ShopReviewLastDate
import com.jjbaksa.domain.usecase.map.GetMapShopUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val useCase: GetMapShopUseCase
) : BaseViewModel() {
    val placeId = SingleLiveEvent<String>()
    val showProgress = SingleLiveEvent<Boolean>()
    val myReviewUpdateCursor = SingleLiveEvent<MyReviewCursor>()
    val friendReviewUpdateCursor = SingleLiveEvent<FriendReviewCursor>()

    private val _reviewLastDate = MutableLiveData<ShopReviewLastDate>()
    val reviewLastDate: LiveData<ShopReviewLastDate> get() = _reviewLastDate

    private val _imageList = MutableLiveData<MutableList<String>>()
    val imageList: LiveData<MutableList<String>> get() = _imageList

    private val _shopInfo = SingleLiveEvent<ShopDetail>()
    val shopInfo: SingleLiveEvent<ShopDetail> get() = _shopInfo

    private val _isReview = SingleLiveEvent<Boolean>()
    val isReview: SingleLiveEvent<Boolean> get() = _isReview

    private val _myReview = SingleLiveEvent<ShopMyReview>()
    val myReview: SingleLiveEvent<ShopMyReview> get() = _myReview

    private val _friendReview = SingleLiveEvent<FollowerShopReview>()
    val friendReview: SingleLiveEvent<FollowerShopReview> get() = _friendReview

    fun setImageList(images: List<String>) {
        _imageList.value = images.toMutableList()
    }

    fun removeImageList(position: Int) {
        _imageList.value?.removeAt(position)
    }

    fun getShopDetail(placeId: String) {
        showProgress.value = true
        viewModelScope.launch(ceh) {
            useCase.getShopDetail(placeId).collect {
                it.onSuccess {
                    _shopInfo.value = it
                    showProgress.value = false
                }.onFailure {
                    it.printStackTrace()
                    _shopInfo.value = ShopDetail()
                }
            }
        }
    }

    fun getShopReviewLastDate(placeId: String) {
        viewModelScope.launch(ceh) {
            useCase.getShopReviewLastDate(placeId).collect {
                it.onSuccess {
                    _reviewLastDate.value = it
                }.onFailure {
                    it.printStackTrace()
                    _reviewLastDate.value = ShopReviewLastDate()
                }
            }
        }
    }

    fun getMyReview(
        placeId: String,
        idCursor: Int? = null,
        dateCursor: String? = null,
        rateCursor: Int? = null,
        size: Int? = null, // 1~10
        direction: String? = null, // asc / desc
        sort: String? = null // createdAt / rate
    ) {
        viewModelScope.launch(ceh) {
            useCase.getMyReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
                .collect {
                    it.onSuccess {
                        _myReview.value = it
                    }.onFailure {
                        it.printStackTrace()
                        _myReview.value = ShopMyReview()
                    }
                }
        }
    }

    fun setReview(placeId: String, content: String, rate: Int, reviewImages: List<String>) {
        viewModelScope.launch(ceh) {
            useCase.setReview(placeId, content, rate, reviewImages).collect {
                it.onSuccess {
                    _isReview.value = true
                }.onFailure {
                    it.printStackTrace()
                    _isReview.value = false
                }
            }
        }
    }

    fun getFollowerShopReview(
        placeId: String,
        idCursor: Int? = null,
        dateCursor: String? = null,
        rateCursor: Int? = null,
        size: Int? = null,
        direction: String? = null,
        sort: String? = null
    ) {
        viewModelScope.launch(ceh) {
            useCase.getFollowerShopReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
                .collect {
                    it.onSuccess {
                        _friendReview.value = it
                    }.onFailure {
                        it.printStackTrace()
                        _friendReview.value = FollowerShopReview()
                    }
                }
        }
    }
}
