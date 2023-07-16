package com.jjbaksa.jjbaksa.ui.changepassword.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {
    var isEnableButton = MutableLiveData<Boolean>(false)

    private val _currentPasswordState = SingleLiveEvent<Boolean>()
    val currentPasswordState: SingleLiveEvent<Boolean> get() = _currentPasswordState

    fun checkPassword(password: String) {
        viewModelScope.launch {
            runCatching {
                repository.checkPassword(password)
            }.onSuccess {
                when (it) {
                    is RespResult.Success -> {
                        _currentPasswordState.value = true
                        Log.d("로그", "checkPassword Success : ${it.data}")
                    }

                    is RespResult.Error -> {
                        _currentPasswordState.value = false
                        Log.d("로그", "checkPassword Error message : ${it.errorType.errorMessage}")
                        Log.d("로그", "checkPassword Error code : ${it.errorType.code}")
                    }
                }
            }.onFailure {

            }
        }
    }

    fun setNewPassword(password: String) {
        viewModelScope.launch {
            repository.setNewPassword(password)
        }
    }

    fun setEnabledButton(enable: Boolean) {
        isEnableButton.value = enable
    }
}
