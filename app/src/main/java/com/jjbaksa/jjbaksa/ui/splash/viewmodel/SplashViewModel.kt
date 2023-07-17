package com.jjbaksa.jjbaksa.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {
    private var _autoLogin = MutableLiveData<Boolean>()
    val autoLogin: LiveData<Boolean> get() = _autoLogin

    private var _authLoginState = MutableLiveData<RespResult<Boolean>>()
    val authLoginState: LiveData<RespResult<Boolean>> get() = _authLoginState

    fun getAutoLogin() {
        _autoLogin.value = repository.getAutoLoginFlag()
    }

    fun getAccessToken() {
        viewModelScope.launch {
            runCatching {
                repository.me()
            }.onSuccess {
                _authLoginState.value = it
            }.onFailure { }
        }
    }
}
