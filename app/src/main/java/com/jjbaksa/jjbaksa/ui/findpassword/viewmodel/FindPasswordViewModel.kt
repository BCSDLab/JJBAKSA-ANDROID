package com.jjbaksa.jjbaksa.ui.findpassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.user.FindPasswordReq
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : BaseViewModel() {
    val userId = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()

    private val _isSuccess = SingleLiveEvent<Boolean>()
    val isSuccess: SingleLiveEvent<Boolean> get() = _isSuccess
    private val _verifyResult = SingleLiveEvent<Boolean>()
    val verifyResult: SingleLiveEvent<Boolean> get() = _verifyResult
    private val _newPasswordResult = SingleLiveEvent<Boolean>()
    val newPasswordResult: SingleLiveEvent<Boolean> get() = _newPasswordResult

    private val _stateBox = SingleLiveEvent<MutableList<Boolean>>()
    val stateBox: SingleLiveEvent<MutableList<Boolean>> get() = _stateBox
    private val _stateBoxNumber = SingleLiveEvent<MutableList<Int?>>()
    val stateBoxNumber: SingleLiveEvent<MutableList<Int?>> get() = _stateBoxNumber

    fun getUserInfo(id: String, email: String) {
        userId.value = id
        userEmail.value = email
    }

    fun postUserEmailPassword(id: String, email: String) {
        viewModelScope.launch(ceh) {
            userUseCase.postUserEmailPassword(id, email) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _isSuccess.value = it
                }
            }
        }
    }

    fun setStateBox(box: MutableList<Boolean>) {
        _stateBox.value = box
    }

    fun setStateBoxNumber(boxNumber: MutableList<Int?>) {
        _stateBoxNumber.value = boxNumber
    }

    fun postUserPassword(code: String) {
        viewModelScope.launch(ceh) {
            userUseCase.postUserPassword(
                FindPasswordReq(
                    userId.value.toString(),
                    userEmail.value.toString(),
                    code
                )
            ) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _verifyResult.value = it
                }
            }

        }
    }

    fun setNewPassword(newPassword: String) {
        viewModelScope.launch(ceh) {
            userUseCase.setNewPassword(newPassword) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _newPasswordResult.value = it
                }
            }
        }
    }
}
