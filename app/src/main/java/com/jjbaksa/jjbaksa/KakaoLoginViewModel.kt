package com.jjbaksa.jjbaksa


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KakaoLoginViewModel @Inject constructor(private val kakaoLoginRepository: KakaoLoginRepository) :
    ViewModel() {

    fun handleKakaoLogin() {
        kakaoLoginRepository.handleKakaoLogin()

    }

}