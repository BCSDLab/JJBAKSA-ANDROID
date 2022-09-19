package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.KakaoLoginRepository

class HandleKakaoLoginUseCase(
    private val kakaoLoginRepository: KakaoLoginRepository,
) {
    operator fun invoke() {
        return kakaoLoginRepository.handleKakaoLogin()
    }
}
