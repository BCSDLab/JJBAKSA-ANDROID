package com.jjbaksa.jjbaksa.ui.inquiry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WriteInquiryViewModel @Inject constructor(
): ViewModel(){
    val inquiryContentLength = MutableLiveData<Int>(0)
    val isCheckableButton = MutableLiveData<Boolean>(false)
}