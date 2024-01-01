package com.jjbaksa.jjbaksa.ui.signup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.enums.SignUpAlertEnum
import com.jjbaksa.domain.model.user.SignUpReq
import com.jjbaksa.domain.usecase.CheckAccountAvailableUseCase
import com.jjbaksa.domain.usecase.SignUpUseCase
import com.jjbaksa.jjbaksa.ui.signup.viewmodel.state.SignUpUIState
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val checkAccountAvailableUseCase: CheckAccountAvailableUseCase,
    private val signUpUseCase: SignUpUseCase
) :
    ViewModel() {

    private val _isSignUpSuccess = MutableLiveData<Boolean>()
    val isSignUpSuccess: LiveData<Boolean>
        get() = _isSignUpSuccess

    private val _uiState  = SingleLiveEvent<SignUpUIState>()
    val uiState: SingleLiveEvent<SignUpUIState> get() = _uiState

    private var availableId = ""

    var id: String = ""
    var email: String = ""
    var password: String = ""
    var nickname: String = ""

    init {
        _uiState.value = SignUpUIState()
    }

    fun checkAccountAvailable(account: String) {

        viewModelScope.launch {
            runCatching {
                checkAccountAvailableUseCase(account)
            }.onSuccess {
                when (it) {
                    is RespResult.Error -> {
                        updateAlertType(SignUpAlertEnum.ID_EXIST)
                        updateAlertState(true)
                    }
                    is RespResult.Success -> {
                        availableId = id
                        updateIdCheckedState(true)
                        updateAlertState(false)
                    }
                }
            }.onFailure {
                // Handle error here
            }
        }
    }

    fun isTypedIdChanged(): Boolean {
        return availableId != id
    }

    fun updateIdCheckedState(newState: Boolean) {
        _uiState.value = _uiState.value?.copy(isIdChecked =  newState)
    }

    fun updateAlertState(newState: Boolean) {
        _uiState.value = _uiState.value?.copy(isAlertShown = newState)
    }

    fun updateAlertType(newAlertType: SignUpAlertEnum) {
        _uiState.value = _uiState.value?.copy(alertType = newAlertType)
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
