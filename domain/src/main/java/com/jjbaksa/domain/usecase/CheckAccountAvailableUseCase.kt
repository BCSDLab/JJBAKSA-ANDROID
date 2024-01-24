package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import javax.inject.Inject

class CheckAccountAvailableUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(account: String): RespResult<Boolean> {
        return userRepository.checkAccountAvailable(account)
    }
}
