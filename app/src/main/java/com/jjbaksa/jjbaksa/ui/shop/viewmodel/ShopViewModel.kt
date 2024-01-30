package com.jjbaksa.jjbaksa.ui.shop.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.review.MyReviewShops
import com.jjbaksa.domain.model.review.ReviewShopLastDate
import com.jjbaksa.domain.model.scrap.AddShopScrap
import com.jjbaksa.domain.model.shop.Period
import com.jjbaksa.domain.model.shop.ShopInfo
import com.jjbaksa.domain.usecase.shop.ShopUseCase
import com.jjbaksa.domain.usecase.review.ReviewUseCase
import com.jjbaksa.domain.usecase.scrap.GetShopScrapUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopUseCase: ShopUseCase,
    private val reviewUseCase: ReviewUseCase,
    private val scrapUseCase: GetShopScrapUseCase
) : BaseViewModel() {
    val placeId = SingleLiveEvent<String>()
    val showProgress = SingleLiveEvent<Boolean>()

    private val _shopInfo = SingleLiveEvent<ShopInfo>()
    val shopInfo: SingleLiveEvent<ShopInfo> get() = _shopInfo

    private val _addScrapInfo = SingleLiveEvent<AddShopScrap>()
    val addScrapInfo: SingleLiveEvent<AddShopScrap> get() = _addScrapInfo

    private val _myReview = SingleLiveEvent<MyReviewShops>()
    val myReview: SingleLiveEvent<MyReviewShops> get() = _myReview

    private val _friendReview = SingleLiveEvent<FollowerReviewShops>()
    val friendReview: SingleLiveEvent<FollowerReviewShops> get() = _friendReview

    private val _followerReviewCount = SingleLiveEvent<Int>()
    val followerReviewCount: SingleLiveEvent<Int> get() = _followerReviewCount

    private val _myLastReviewDate = SingleLiveEvent<ReviewShopLastDate>()
    val myLastReviewDate: SingleLiveEvent<ReviewShopLastDate> get() = _myLastReviewDate

    private val _shopAverageRate = SingleLiveEvent<Float>()
    val shopAverageRate: SingleLiveEvent<Float> get() = _shopAverageRate

    val formattedPhoneNumber = SingleLiveEvent<String>()
    val period = SingleLiveEvent<Period>()

    fun getShopInfo(placeId: String) {
        showProgress.value = true
        viewModelScope.launch(ceh) {
            shopUseCase.getShopInfo(placeId) { msg ->
                toastMsg.postValue(msg)
            }.collect {
                it.onSuccess {
                    showProgress.value = false
                    _shopInfo.value = it
                    formattedPhoneNumber.value = it.formattedPhoneNumber.ifEmpty { "정보 없음" }
                    period.value = it.todayPeriod
                }
                it.onFailure {
                    showProgress.value = false
                    _shopInfo.value = ShopInfo()
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
            reviewUseCase.getMyReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
                .collect {
                    it.onSuccess {
                        _myReview.value = it
                    }.onFailure {
                        _myReview.value = MyReviewShops()
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
            reviewUseCase.getFollowerReviewShops(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
                .collect {
                    it.onSuccess {
                        _friendReview.value = it
                    }
                    it.onFailure {
                        _friendReview.value = FollowerReviewShops()
                    }
                }
        }
    }

    fun getMyLastReviewDate(placeId: String) {
        viewModelScope.launch(ceh) {
            reviewUseCase.getMyReviewShopLastDate(placeId).collect {
                it.onSuccess {
                    _myLastReviewDate.value = it
                }.onFailure {
                    _myLastReviewDate.value = ReviewShopLastDate()
                }
            }
        }
    }

    fun getFollowersShopReviewCount(placeId: String) {
        viewModelScope.launch(ceh) {
            reviewUseCase.getFollowersShopReviewCount(placeId).collect {
                it.onSuccess {
                    _followerReviewCount.value = it
                }.onFailure {
                    _followerReviewCount.value = 0
                }
            }
        }
    }

    fun deleteShopScrap(scrapId: Int) {
        viewModelScope.launch(ceh) {
            scrapUseCase.deleteShopScrap(scrapId).collect {
                it.onSuccess {
                    _addScrapInfo.value = AddShopScrap()
                }.onFailure {
                }
            }
        }
    }

    fun getShopRates(placeId: String) {
        viewModelScope.launch(ceh) {
            shopUseCase.getShopsRates(placeId) { msg ->
                toastMsg.postValue(msg)
            }.collect {
                it.onSuccess {
                    _shopAverageRate.value = it
                }.onFailure {
                    _shopAverageRate.value = 0f
                }
            }
        }
    }
}
