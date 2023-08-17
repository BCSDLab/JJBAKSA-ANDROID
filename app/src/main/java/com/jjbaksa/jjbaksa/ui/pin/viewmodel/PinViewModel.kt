package com.jjbaksa.jjbaksa.ui.pin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.enums.FriendReviewCursor
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.enums.PinReviewCursor
import com.jjbaksa.domain.resp.follower.FollowerShopReview
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.resp.map.ShopReviewLastDate
import com.jjbaksa.domain.resp.scrap.AddShopScrap
import com.jjbaksa.domain.usecase.map.GetMapShopUseCase
import com.jjbaksa.domain.usecase.scrap.GetShopScrapUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val useCase: GetMapShopUseCase,
    private val scrapUseCase: GetShopScrapUseCase
) : BaseViewModel() {
    val placeId = SingleLiveEvent<String>()
    val showProgress = SingleLiveEvent<Boolean>()
    val myReviewUpdateCursor = SingleLiveEvent<MyReviewCursor>()
    val friendReviewUpdateCursor = SingleLiveEvent<FriendReviewCursor>()
    val pinReviewCursor = SingleLiveEvent<PinReviewCursor>()

    private val _errorHandler = SingleLiveEvent<ErrorType>()
    val errorHandler: SingleLiveEvent<ErrorType> get() = _errorHandler

    private val _myReviewLastDate = MutableLiveData<ShopReviewLastDate>()
    val myReviewLastDate: LiveData<ShopReviewLastDate> get() = _myReviewLastDate

    private val _friendReviewLastDate = MutableLiveData<ShopReviewLastDate>()
    val friendReviewLastDate: LiveData<ShopReviewLastDate> get() = _friendReviewLastDate

    private val _shopInfo = SingleLiveEvent<ShopDetail>()
    val shopInfo: SingleLiveEvent<ShopDetail> get() = _shopInfo

    private val _addScrapInfo = SingleLiveEvent<AddShopScrap>()
    val addScrapInfo: SingleLiveEvent<AddShopScrap> get() = _addScrapInfo

    private val _myReview = SingleLiveEvent<ShopMyReview>()
    val myReview: SingleLiveEvent<ShopMyReview> get() = _myReview

    private val _friendReview = SingleLiveEvent<FollowerShopReview>()
    val friendReview: SingleLiveEvent<FollowerShopReview> get() = _friendReview

    fun getShopDetail(placeId: String) {
        showProgress.value = true
        viewModelScope.launch(ceh) {
            useCase.getShopDetail(placeId).collect {
                it.onSuccess {
                    when (it) {
                        is RespResult.Success -> {
                            _shopInfo.value = it.data!!
                            showProgress.value = false
                        }
                        is RespResult.Error -> {
                            showProgress.value = false
                            _errorHandler.value = it.errorType
                        }
                    }
                }.onFailure {
                    it.printStackTrace()
                    _shopInfo.value = ShopDetail()
                }
            }
        }
    }

    fun addShopScrap(directoryId: Int, placeId: String) {
        viewModelScope.launch(ceh) {
            scrapUseCase.addShopScrap(directoryId, placeId).collect {
                it.onSuccess {
                    _addScrapInfo.value = it
                }.onFailure {
                    it.printStackTrace()
                    _addScrapInfo.value = AddShopScrap()
                }
            }
        }
    }

    fun getShopReviewLastDate(placeId: String) {
        viewModelScope.launch(ceh) {
            useCase.getShopReviewLastDate(placeId).collect {
                it.onSuccess {
                    _myReviewLastDate.value = it
                }.onFailure {
                    it.printStackTrace()
                    _myReviewLastDate.value = ShopReviewLastDate()
                }
            }
        }
    }

    fun getShopFollowerReviewLastDate(placeId: String) {
        viewModelScope.launch(ceh) {
            useCase.getShopFollowerReviewLastDate(placeId).collect {
                it.onSuccess {
                    _myReviewLastDate.value = it
                }.onFailure {
                    it.printStackTrace()
                    _myReviewLastDate.value = ShopReviewLastDate()
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
