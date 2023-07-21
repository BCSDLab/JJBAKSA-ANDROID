package com.jjbaksa.jjbaksa.ui.withdrawal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {
    val userNickname = MutableLiveData<String>("")
    private val _isEnabled = SingleLiveEvent<Boolean>()
    val isEnabled: LiveData<Boolean> get() = _isEnabled
    val inputTextLength = MutableLiveData<String>("0")
    private val _maxInputTextLength = SingleLiveEvent<Boolean>()
    val maxInputTextLength: LiveData<Boolean> get() = _maxInputTextLength
    val reason = MutableLiveData<String>("")

    fun getNickname() {
        userNickname.value = repository.getNickname()
    }

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
}
