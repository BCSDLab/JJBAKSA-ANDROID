package com.jjbaksa.jjbaksa


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KakaoLoginViewModel @Inject constructor(private val handleKakaoLoginUseCase: HandleKakaoLoginUseCase) :
    ViewModel() {

    fun handleKakaoLogin() {
        handleKakaoLoginUseCase()

    }

}