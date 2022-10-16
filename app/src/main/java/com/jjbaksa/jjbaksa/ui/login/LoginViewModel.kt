package com.jjbaksa.jjbaksa.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    val account = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val isAutoLogin = MutableLiveData<Boolean>(false)

    private val _loginState = MutableLiveData<LoginResult>(LoginResult())
    val loginState: LiveData<LoginResult> get() = _loginState

    fun login() {
        viewModelScope.launch {
            repository.postLogin(account.value!!, password.value!!, isAutoLogin.value!!) {
                _loginState.value = it
            }
        }
    }
}
