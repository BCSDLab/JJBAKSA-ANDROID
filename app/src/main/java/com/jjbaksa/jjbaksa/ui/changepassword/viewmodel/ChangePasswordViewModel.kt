package com.jjbaksa.jjbaksa.ui.changepassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    val repository: UserRepository
) : BaseViewModel() {
    var isEnableButton = MutableLiveData<Boolean>(false)

    private val _passwordResult = SingleLiveEvent<Boolean>()
    val passwordResult: SingleLiveEvent<Boolean> get() = _passwordResult

    private val _newPasswordResult = SingleLiveEvent<Boolean>()
    val newPasswordResult: SingleLiveEvent<Boolean> get() = _newPasswordResult

    fun setEnabledButton(enable: Boolean) {
        isEnableButton.value = enable
    }

    fun checkPassword(password: String) {
        viewModelScope.launch(ceh) {
            userUseCase.postUserCheckPassword(password) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _passwordResult.value = it
                }
            }
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
}
