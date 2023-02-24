package com.jjbaksa.jjbaksa.ui.findpassword.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    private val _userAccount = SingleLiveEvent<String>()
    val userAccount: SingleLiveEvent<String> get() = _userAccount

    private val _userToken = SingleLiveEvent<String?>()
    val userToken: SingleLiveEvent<String?> get() = _userToken

    private val _authEmailState = SingleLiveEvent<RespResult<Boolean>>()
    val authEmailState: SingleLiveEvent<RespResult<Boolean>> get() = _authEmailState

    private val _existAccount = SingleLiveEvent<RespResult<Boolean>>()
    val existAccount: SingleLiveEvent<RespResult<Boolean>> get() = _existAccount

    private val _buttonEnabled = MutableLiveData<MutableList<Boolean>>()
    val buttonEnabled: MutableLiveData<MutableList<Boolean>> get() = _buttonEnabled

    private val _isChangePassword = SingleLiveEvent<Boolean>()
    val isChangePassword: SingleLiveEvent<Boolean> get() = _isChangePassword

    fun isExistAccount(account: String) {
        viewModelScope.launch(ceh) {
            repository.checkAccountAvailable(account).let {
                _existAccount.value = it
            }
        }
    }

    fun getButtonEnabled(state: MutableList<Boolean>) {
        _buttonEnabled.value = state
    }

    fun getAuthEmail(email: String) {
        viewModelScope.launch(ceh) {
            repository.checkAuthEmail(email).let {
                _authEmailState.value = it
            }
        }
    }

    fun findPassword(account: String, email: String, code: String) {
        viewModelScope.launch(ceh) {
            repository.findPassword(account, email, code).let { token ->
                _userToken.value = token
            }
        }
    }

    fun setNewPassword(newPassword: String) {
        viewModelScope.launch(ceh) {
            repository.changeUserPassword(newPassword).let {
                _isChangePassword.value = it
            }
        }
    }
}
