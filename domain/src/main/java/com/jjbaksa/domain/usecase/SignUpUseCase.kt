package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.resp.user.SignUpResp
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(signUpReq: SignUpReq): SignUpResp? {
        return userRepository.postSignUp(signUpReq)
    }
}
