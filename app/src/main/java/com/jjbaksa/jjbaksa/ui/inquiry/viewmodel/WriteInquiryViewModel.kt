package com.jjbaksa.jjbaksa.ui.inquiry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteInquiryViewModel @Inject constructor() : ViewModel() {
    val inquiryContentLength = MutableLiveData<Int>(0)
    val isCheckableButton = MutableLiveData<Boolean>(false)
    val isExtraPhoto = MutableLiveData<Boolean>(false)
    val isVisibleAddPhoto = MutableLiveData<Boolean>()

    private val _photoList = MutableLiveData<MutableList<String>>()
    val photoList: LiveData<MutableList<String>> get() = _photoList

    fun setPhotoList(photoList: List<String>) {
        this._photoList.value = photoList.toMutableList()
        isVisibleAddPhoto.value = photoList.size != 3
    }

    fun removePhotoListItem(position: Int) {
        _photoList.value?.removeAt(position)
        isExtraPhoto.value = _photoList.value?.size != 0
        isVisibleAddPhoto.value = true
    }
}
