package com.jjbaksa.jjbaksa.ui.withdrawal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    val repository: UserRepository
) : BaseViewModel() {
    private val _isEnabled = SingleLiveEvent<Boolean>()
    val isEnabled: LiveData<Boolean> get() = _isEnabled
    val inputTextLength = MutableLiveData<String>("0")
    private val _maxInputTextLength = SingleLiveEvent<Boolean>()
    val maxInputTextLength: LiveData<Boolean> get() = _maxInputTextLength
    val reason = MutableLiveData<String>("")

    private val _withdrawalReasonResult = SingleLiveEvent<Boolean>()
    val withdrawalReasonResult: SingleLiveEvent<Boolean> get() = _withdrawalReasonResult
    private val _withdrawalResult = SingleLiveEvent<Boolean>()
    val withdrawalResult: SingleLiveEvent<Boolean> get() = _withdrawalResult

    fun setIsEnabled(enabled: Boolean) {
        _isEnabled.value = enabled
    }

    fun setInputTextLength(length: String) {
        inputTextLength.value = length
    }

    fun setMaxInputTextLength(maxLength: Boolean) {
        _maxInputTextLength.value = maxLength
    }

    fun setWithdrawalReason(radioReason: String) {
        reason.value = radioReason
    }

    fun postUserWithdrawalReason(withdrawalReason: WithdrawalReasonReq) {
        viewModelScope.launch(ceh) {
            userUseCase.postUserWithdrawalReason(withdrawalReason) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _withdrawalReasonResult.value = it
                }
            }
        }
    }

    fun withdraw() {
        viewModelScope.launch(ceh) {
            repository.deleteUserMe { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _withdrawalResult.value = it
                }
            }
        }
    }
}
