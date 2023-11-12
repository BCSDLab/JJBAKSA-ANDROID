package com.jjbaksa.jjbaksa.ui.inquiry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.inquiry.InquiryContent
import com.jjbaksa.domain.usecase.inquiry.InquiryUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteInquiryViewModel @Inject constructor(
    private val inquiryUseCase: InquiryUseCase
) : BaseViewModel() {
    val inquiryContentLength = MutableLiveData<Int>(0)
    val isCheckableButton = MutableLiveData<Boolean>(false)
    val isExtraPhoto = MutableLiveData<Boolean>(false)
    val isVisibleAddPhoto = MutableLiveData<Boolean>()

    private val _photoList = MutableLiveData<MutableList<String>>()
    val photoList: LiveData<MutableList<String>> get() = _photoList

    private val _inquiryContent = SingleLiveEvent<InquiryContent>()
    val inquiryContent: SingleLiveEvent<InquiryContent> get() = _inquiryContent

    fun setPhotoList(photoList: List<String>) {
        this._photoList.value = photoList.toMutableList()
        isVisibleAddPhoto.value = photoList.size != 3
    }

    fun removePhotoListItem(position: Int) {
        _photoList.value?.removeAt(position)
        isExtraPhoto.value = _photoList.value?.size != 0
        isVisibleAddPhoto.value = true
    }

    fun setInquiry(title: String, content: String, isSecret: Boolean, inquiryImages: List<String>) {
        viewModelScope.launch(ceh) {
            inquiryUseCase.setInquiry(title, content, isSecret, inquiryImages).collect {
                it.onSuccess {
                    _inquiryContent.value = it
                }
            }
        }
    }
}
