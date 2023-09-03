package com.jjbaksa.jjbaksa.ui.inquiry.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.inquiry.InquiryData
import com.jjbaksa.domain.usecase.inquiry.GetAllInquiryUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
    private val getAllInquiryUseCase: GetAllInquiryUseCase
) : BaseViewModel() {
    private val _inquiryData = SingleLiveEvent<InquiryData>()
    val inquiryData: SingleLiveEvent<InquiryData> get() = _inquiryData

    fun getInquiry(idCursor: String, dateCursor: String, size: Int) {
        viewModelScope.launch(ceh) {
            getAllInquiryUseCase.invoke(idCursor, dateCursor, size).collect {
                it.onSuccess { data -> _inquiryData.value = data }
            }
        }
    }
}
