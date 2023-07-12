package com.jjbaksa.jjbaksa.ui.splash.viewmodel

import android.util.Log
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

    private var _authLoginState = MutableLiveData<Boolean>()
    val authLoginState: LiveData<Boolean> get() = _authLoginState

    fun getAutoLogin() {
        _autoLogin.value = repository.getAutoLoginFlag()
    }

    fun getAccessToken() {
        viewModelScope.launch {
            runCatching {
                repository.me()
            }.onSuccess {
                when(it) {
                    is RespResult.Success -> {
                        _authLoginState.value = it.data!!
                    }
                    is RespResult.Error -> {
                        Log.d("로그", "errorMessage : ${it.errorType.errorMessage}")
                        Log.d("로그", "errorCode : ${it.errorType.code}")
                    }
                }
            }.onFailure {  }
        }
    }
}