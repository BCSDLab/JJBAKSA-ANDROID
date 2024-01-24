package com.jjbaksa.jjbaksa.ui.follower.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.domain.model.inquiry.Inquiry
import com.jjbaksa.domain.usecase.follower.FollowerUseCase
import com.jjbaksa.domain.usecase.inquiry.InquiryUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val followerUseCase: FollowerUseCase
) : BaseViewModel() {

}
