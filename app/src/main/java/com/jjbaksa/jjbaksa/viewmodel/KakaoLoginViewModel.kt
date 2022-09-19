package com.jjbaksa.jjbaksa.viewmodel

import androidx.lifecycle.ViewModel
import com.jjbaksa.domain.usecase.HandleKakaoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KakaoLoginViewModel @Inject constructor(
    private val handleKakaoLoginUseCase: HandleKakaoLoginUseCase,
) : ViewModel() {

    fun handleKakaoLogin() {
        handleKakaoLoginUseCase()
    }
}
