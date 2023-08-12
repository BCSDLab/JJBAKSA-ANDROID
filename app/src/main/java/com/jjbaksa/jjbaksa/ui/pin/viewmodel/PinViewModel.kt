package com.jjbaksa.jjbaksa.ui.pin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.resp.map.ShopDetail
import com.jjbaksa.domain.resp.map.ShopMyReview
import com.jjbaksa.domain.usecase.map.GetMapShopUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val useCase: GetMapShopUseCase
) : BaseViewModel() {
    val placeId = SingleLiveEvent<String>()
    val showProgress = SingleLiveEvent<Boolean>()
    val myReviewUpdateCursor = SingleLiveEvent<MyReviewCursor>()

    private val _imageList = MutableLiveData<MutableList<String>>()
    val imageList: LiveData<MutableList<String>> get() = _imageList

    private val _shopInfo = SingleLiveEvent<ShopDetail>()
    val shopInfo: SingleLiveEvent<ShopDetail> get() = _shopInfo

    private val _isReview = SingleLiveEvent<Boolean>()
    val isReview: SingleLiveEvent<Boolean> get() = _isReview

    private val _myReview = SingleLiveEvent<ShopMyReview>()
    val myReview: SingleLiveEvent<ShopMyReview> get() = _myReview

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
                    Log.e("로그", "$it")
                    Log.e("로그", "${showProgress.value}")
                }.onFailure {
                    it.printStackTrace()
                    _shopInfo.value = ShopDetail()
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
}
