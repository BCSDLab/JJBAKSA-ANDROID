package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() {}
}
