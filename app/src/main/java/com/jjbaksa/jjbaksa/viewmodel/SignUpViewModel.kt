package com.jjbaksa.jjbaksa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.enums.SignUpAlertEnum
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.domain.usecase.CheckIdAvailableUseCase
import com.jjbaksa.domain.usecase.SignUpUseCase
import com.jjbaksa.jjbaksa.viewmodel.state.SignUpUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkIdAvailableUseCase: CheckIdAvailableUseCase,
    private val signUpUseCase: SignUpUseCase
) :
    ViewModel() {

    private val _isIdAvailable = MutableLiveData<Boolean>()
    val isIdAvailable: LiveData<Boolean>
        get() = _isIdAvailable

    private val _isSignUpSuccess = MutableLiveData<Boolean>()
    val isSignUpSuccess: LiveData<Boolean>
        get() = _isSignUpSuccess

    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    var id: String = ""
    var email: String = ""
    var password: String = ""
    var nickname: String = ""

    fun checkAccountAvailable(account: String) {
        viewModelScope.launch {
            runCatching {
                checkIdAvailableUseCase(account)
            }.onSuccess { result ->
                _isIdAvailable.value = result
                if (!result) {
                    updateAlertType(SignUpAlertEnum.ID_EXIST)
                    updateAlertState(true)
                }
            }.onFailure {
                // Handle error here
            }
        }
    }

    fun updateIdCheckedState(newState: Boolean) {
        _uiState.update {
            it.copy(isIdChecked = newState)
        }
    }

    fun updateAlertState(newState: Boolean) {
        _uiState.update {
            it.copy(isAlertShown = newState)
        }
    }

    fun updateAlertType(newAlertType: SignUpAlertEnum) {
        _uiState.update {
            it.copy(alertType = newAlertType)
        }
    }

    fun signUpRequest() {
        val signUpReq = SignUpReq(id, email, nickname, password)
        viewModelScope.launch {
            runCatching {
                signUpUseCase(signUpReq)
            }.onSuccess {
                _isSignUpSuccess.value = true
            }.onFailure {
                // Handle sign up fail throwable
            }
        }
    }
}
