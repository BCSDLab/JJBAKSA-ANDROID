package com.jjbaksa.jjbaksa.ui.changepassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {
    var isEnableButton = MutableLiveData<Boolean>(false)

    private val _currentPasswordState = SingleLiveEvent<FormatResp>()
    val currentPasswordState: SingleLiveEvent<FormatResp> get() = _currentPasswordState

    private val _newPasswordState = SingleLiveEvent<FormatResp>()
    val newPasswordState: SingleLiveEvent<FormatResp> get() = _newPasswordState

    fun checkPassword(password: String) {
        viewModelScope.launch {
            _currentPasswordState.value = repository.checkPassword(password)
        }
    }

    fun setNewPassword(password: String) {
        viewModelScope.launch {
            _newPasswordState.value = repository.setNewPassword(password)
        }
    }

    fun setEnabledButton(enable: Boolean) {
        isEnableButton.value = enable
    }
}
