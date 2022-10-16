package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.UserRepository
import javax.inject.Inject

class CheckAccountAvailableUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(account: String): Boolean {
        return userRepository.checkAccountAvailable(account)
    }
}
