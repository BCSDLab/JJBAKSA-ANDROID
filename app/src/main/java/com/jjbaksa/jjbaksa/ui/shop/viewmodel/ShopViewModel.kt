package com.jjbaksa.jjbaksa.ui.shop.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.FriendReviewCursor
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.enums.PinReviewCursor
import com.jjbaksa.domain.model.review.FollowerReviewShops
import com.jjbaksa.domain.model.shop.ShopDetail
import com.jjbaksa.domain.model.review.MyReviewShops
import com.jjbaksa.domain.model.review.ReviewShopLastDate
import com.jjbaksa.domain.model.scrap.AddShopScrap
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

    private val _shopInfo = SingleLiveEvent<ShopDetail>()
    val shopInfo: SingleLiveEvent<ShopDetail> get() = _shopInfo

    private val _addScrapInfo = SingleLiveEvent<AddShopScrap>()
    val addScrapInfo: SingleLiveEvent<AddShopScrap> get() = _addScrapInfo

    private val _myReview = SingleLiveEvent<MyReviewShops>()
    val myReview: SingleLiveEvent<MyReviewShops> get() = _myReview

    private val _friendReview = SingleLiveEvent<FollowerReviewShops>()
    val friendReview: SingleLiveEvent<FollowerReviewShops> get() = _friendReview


    fun getShopDetail(placeId: String) {
        showProgress.value = true
        viewModelScope.launch(ceh) {
            shopUseCase.getShopDetail(placeId) { msg ->
                toastMsg.postValue(msg)
            }.collect {
                it.onSuccess {
                    showProgress.value = false
                    _shopInfo.value = it
                    Log.d("ShopViewModel", "getShopDetail: $it")
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
}
