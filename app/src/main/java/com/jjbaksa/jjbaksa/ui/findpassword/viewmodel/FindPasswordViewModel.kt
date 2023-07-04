package com.jjbaksa.jjbaksa.ui.findpassword.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.FindPasswordReq
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.domain.resp.user.UserInfo
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo
    private val _isPasswordVerificationCode = SingleLiveEvent<FormatResp>()
    val isPasswordVerificationCode: SingleLiveEvent<FormatResp> get() = _isPasswordVerificationCode
    private val _verificationCodeResult = SingleLiveEvent<FormatResp>()
    val verificationCodeResult: SingleLiveEvent<FormatResp> get() = _verificationCodeResult

    private val _stateBox = SingleLiveEvent<MutableList<Boolean>>()
    val stateBox: SingleLiveEvent<MutableList<Boolean>> get() = _stateBox
    private val _stateBoxNumber = SingleLiveEvent<MutableList<Int?>>()
    val stateBoxNumber: SingleLiveEvent<MutableList<Int?>> get() = _stateBoxNumber
    private val _newPasswordResult = SingleLiveEvent<Boolean>()
    val newPasswordResult: SingleLiveEvent<Boolean> get() = _newPasswordResult

    fun getUserInfo(id: String, email: String) {
        _userInfo.value = UserInfo(id, email)
    }

    fun getPasswordVerificationCode(id: String, email: String) {
        viewModelScope.launch(ceh) {
            repository.getPasswordVerificationCode(id, email).let {
                _isPasswordVerificationCode.value = it
            }
        }
    }

    fun setStateBox(box: MutableList<Boolean>) {
        _stateBox.value = box
    }

    fun setStateBoxNumber(boxNumber: MutableList<Int?>) {
        _stateBoxNumber.value = boxNumber
    }

    fun findPassword(code: String) {
        viewModelScope.launch(ceh) {
            repository.findPassword(
                FindPasswordReq(
                    userInfo.value?.id!!,
                    userInfo.value?.email!!,
                    code
                )
            ).let {
                _verificationCodeResult.value = it
            }
        }
    }

    fun setNewPassword(newPassword: String) {
        viewModelScope.launch(ceh) {
            repository.setNewPassword(newPassword).let {
                _newPasswordResult.value = it
            }
        }
    }
}
