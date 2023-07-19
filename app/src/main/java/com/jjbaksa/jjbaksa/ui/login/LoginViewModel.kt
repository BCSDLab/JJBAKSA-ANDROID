package com.jjbaksa.jjbaksa.ui.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    val account = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val isAutoLogin = MutableLiveData<Boolean>(false)

    private val _loginState = SingleLiveEvent<LoginResult>()
    val loginState: SingleLiveEvent<LoginResult> get() = _loginState

    private val _autoLoginState = SingleLiveEvent<Boolean>()
    val autoLoginState: SingleLiveEvent<Boolean> get() = _autoLoginState

    fun login(isCheckedSwitch: Boolean) {
        if (!TextUtils.isEmpty(account.value) && !TextUtils.isEmpty(password.value)) {
            viewModelScope.launch(ceh) {
                repository.postLogin(account.value!!, password.value!!, isCheckedSwitch) {
                    _loginState.value = it
                }
            }
        }
    }

//    fun getAutoLoginFlag() {
//        viewModelScope.launch(ceh) {
//
//            if (repository.getAutoLoginFlag()) {
//                isAutoLogin.value = true
//                account.value = repository.getAccount()
//                password.value = repository.getPasswrod()
//                login()
//            }
//        }
//    }
}
