package com.jjbaksa.jjbaksa.ui.withdrawal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import com.jjbaksa.domain.model.user.WithdrawalReasonResp
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {
    private val _isEnabled = SingleLiveEvent<Boolean>()
    val isEnabled: LiveData<Boolean> get() = _isEnabled
    val inputTextLength = MutableLiveData<String>("0")
    private val _maxInputTextLength = SingleLiveEvent<Boolean>()
    val maxInputTextLength: LiveData<Boolean> get() = _maxInputTextLength
    val reason = MutableLiveData<String>("")

    private val _saveWithdrawalReasonState = MutableLiveData<WithdrawalReasonResp>()
    val saveWithdrawalReasonState: LiveData<WithdrawalReasonResp> get() = _saveWithdrawalReasonState
    private val _isWithdrawUser = MutableLiveData<Boolean>()
    val isWithdrawUser: LiveData<Boolean> get() = _isWithdrawUser

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

    fun saveWithdrawalReason(withdrawalReason: WithdrawalReasonReq) {
        viewModelScope.launch {
            runCatching {
                repository.saveWithdrawalReason(withdrawalReason)
            }.onSuccess { result ->
                when (result) {
                    is RespResult.Success -> {
                        _saveWithdrawalReasonState.value = WithdrawalReasonResp(result.data, null)
                    }
                    is RespResult.Error -> {
                        _saveWithdrawalReasonState.value = WithdrawalReasonResp(false, result.errorType.errorMessage)
                    }
                }
            }.onFailure { }
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            runCatching {
                repository.deleteUser()
            }.onSuccess {
                when (it) {
                    is RespResult.Success -> {
                        _isWithdrawUser.value = it.data!!
                    }
                    is RespResult.Error -> {
                        _isWithdrawUser.value = false
                    }
                }
            }.onFailure { }
        }
    }
}
