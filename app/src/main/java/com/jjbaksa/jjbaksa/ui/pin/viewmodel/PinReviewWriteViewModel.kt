package com.jjbaksa.jjbaksa.ui.pin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.usecase.map.GetMapShopUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinReviewWriteViewModel @Inject constructor(
    private val useCase: GetMapShopUseCase
) : BaseViewModel() {
    val placeId = SingleLiveEvent<String>()

    private val _imageList = MutableLiveData<MutableList<String>>()
    val imageList: LiveData<MutableList<String>> get() = _imageList

    private val _isReview = SingleLiveEvent<Boolean>()
    val isReview: SingleLiveEvent<Boolean> get() = _isReview

    fun setImageList(images: List<String>) {
        _imageList.value = images.toMutableList()
    }

    fun removeImageList(position: Int) {
        _imageList.value?.removeAt(position)
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
