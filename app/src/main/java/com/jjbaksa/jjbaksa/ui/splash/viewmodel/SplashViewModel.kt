package com.jjbaksa.jjbaksa.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userUseCase: UserUseCase
) : BaseViewModel() {
    private var _autoLogin = MutableLiveData<Boolean>()
    val autoLogin: LiveData<Boolean> get() = _autoLogin

    private var _authLoginState = MutableLiveData<Boolean>()
    val authLoginState: LiveData<Boolean> get() = _authLoginState

    fun getAutoLogin() {
        _autoLogin.value = userRepository.getAutoLoginFlag()
    }

    fun getUserMe() {
        viewModelScope.launch(ceh) {
            userUseCase.getUserMe().collect {
                it.onSuccess {
                    _authLoginState.value = !it.id.equals(0)
                }
            }
        }
    }
}
