package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.model.user.SignUpResp
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(signUpReq: SignUpReq, onError: (String) -> Unit): SignUpResp? {
        return userRepository.postSignUp(signUpReq, onError)
    }
}
