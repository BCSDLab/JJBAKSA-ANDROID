package com.jjbaksa.jjbaksa.ui.login.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.user.Login
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : BaseViewModel() {
    val account = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val isAutoLogin = MutableLiveData<Boolean>(false)

    private val _loginResult = SingleLiveEvent<Login>()
    val loginResult: SingleLiveEvent<Login> get() = _loginResult

    fun login(isCheckedSwitch: Boolean) {
        if (!TextUtils.isEmpty(account.value) && !TextUtils.isEmpty(password.value)) {
            viewModelScope.launch(ceh) {
                userUseCase.postLogin(account.value ?: "", password.value ?: "", isCheckedSwitch).collect {
                    it.onSuccess {
                        _loginResult.value = it
                    }
                }
            }
        }
    }
}
