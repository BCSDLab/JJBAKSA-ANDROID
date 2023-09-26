package com.jjbaksa.jjbaksa.ui.inquiry.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.usecase.inquiry.InquiryUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
    private val inquiryUseCase: InquiryUseCase
) : BaseViewModel() {
    val inquiryHasMore = SingleLiveEvent<Boolean>()
    val inquiryMeHasMore = SingleLiveEvent<Boolean>()

    private val _inquiryCursor = SingleLiveEvent<InquiryCursor>()
    val inquiryCursor: SingleLiveEvent<InquiryCursor> get() = _inquiryCursor

    private val _inquiry = SingleLiveEvent<Inquiry>()
    val inquiry: SingleLiveEvent<Inquiry> get() = _inquiry

    private val _inquiryMe = SingleLiveEvent<Inquiry>()
    val inquiryMe: SingleLiveEvent<Inquiry> get() = _inquiryMe

    fun getInquiry(idCursor: Long?, dateCursor: String?, size: Int) {
        viewModelScope.launch(ceh) {
            inquiryUseCase.getInquiry(idCursor, dateCursor, size).collect {
                it.onSuccess {
                    _inquiry.value = it
                    inquiryHasMore.value = it.content.count() == 10
                }
            }
        }
    }

    fun getInquiryMe(idCursor: Long?, dateCursor: String?, size: Int) {
        viewModelScope.launch(ceh) {
            inquiryUseCase.getInquiryMe(idCursor, dateCursor, size).collect {
                it.onSuccess {
                    _inquiryMe.value = it
                    inquiryMeHasMore.value = it.content.count() == 10
                }
            }
        }
    }

    fun getInquirySearch(idCursor: Long?, dateCursor: String?, size: Int, searchWord: String) {
        viewModelScope.launch(ceh) {
            inquiryUseCase.getInquirySearch(searchWord, dateCursor, idCursor, size).collect {
                it.onSuccess {
                    _inquiry.value = it
                    inquiryHasMore.value = it.content.count() == 10
                }
            }
        }
    }

    fun getInquirySearchMe(idCursor: Long?, dateCursor: String?, size: Int, searchWord: String) {
        viewModelScope.launch(ceh) {
            inquiryUseCase.getInquirySearchMe(searchWord, dateCursor, idCursor, size).collect {
                it.onSuccess {
                    _inquiryMe.value = it
                    inquiryMeHasMore.value = it.content.count() == 10
                }
            }
        }
    }
}
