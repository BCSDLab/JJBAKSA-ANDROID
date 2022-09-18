package com.jjbaksa.jjbaksa

class HandleKakaoLoginUseCase(private val kakaoLoginRepository: KakaoLoginRepository) {
    operator fun invoke(){
        return kakaoLoginRepository.handleKakaoLogin()
    }
}