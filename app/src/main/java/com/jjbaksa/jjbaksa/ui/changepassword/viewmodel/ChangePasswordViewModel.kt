package com.jjbaksa.jjbaksa.ui.changepassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    val repository: UserRepository
) : BaseViewModel() {
    var isEnableButton = MutableLiveData<Boolean>(false)

    private val _currentPasswordState = SingleLiveEvent<FormatResp?>()
    val currentPasswordState: SingleLiveEvent<FormatResp?> get() = _currentPasswordState

    private val _newPasswordResult = SingleLiveEvent<Boolean>()
    val newPasswordResult: SingleLiveEvent<Boolean> get() = _newPasswordResult

    fun checkPassword(password: String) {
        viewModelScope.launch(ceh) {
            _currentPasswordState.value = repository.checkPassword(password)
        }
    }

    fun setNewPassword(password: String) {
        viewModelScope.launch(ceh) {
            userUseCase.setNewPassword(password) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _newPasswordResult.value = it
                }
            }
        }
    }

    fun setEnabledButton(enable: Boolean) {
        isEnableButton.value = enable
    }
}
