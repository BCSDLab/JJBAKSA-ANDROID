package com.jjbaksa.jjbaksa.ui.social

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialLoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _kakaoLoginState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val kakaoLoginState get() = _kakaoLoginState.asStateFlow()

    fun postLoginSNS(token: String, snsType: String) {
        viewModelScope.launch {
            userUseCase.postLoginSNS(token, snsType).onSuccess {
                _kakaoLoginState.value = UiState.Success(true)
            }.onFailure {
                _kakaoLoginState.value = UiState.Failure(it.message.toString())
            }
        }
    }
}