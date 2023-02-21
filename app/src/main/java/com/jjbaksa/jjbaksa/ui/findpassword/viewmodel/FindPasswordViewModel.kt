package com.jjbaksa.jjbaksa.ui.findpassword.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    val userEmail = MutableLiveData<String>("")
    val codeBoxState = MutableLiveData(mutableListOf(false, false, false, false))

    private val _authEmailState = SingleLiveEvent<RespResult<Boolean>>()
    val authEmailState: SingleLiveEvent<RespResult<Boolean>> get() = _authEmailState

    private val _codeNumber = MutableLiveData<StringBuilder>()
    val codeNumber: MutableLiveData<StringBuilder> get() = _codeNumber

    private val _isSuccessCode = SingleLiveEvent<Boolean>()
    val isSuccessCode: SingleLiveEvent<Boolean> get() = _isSuccessCode

    private val _buttonEnabled = MutableLiveData<MutableList<Boolean>>()
    val buttonEnabled: MutableLiveData<MutableList<Boolean>> get() = _buttonEnabled

    fun getCodeNumber(one:String, two:String, three:String, four:String) {
        _codeNumber.value = StringBuilder(one + two + three + four)
    }

    fun getButtonEnabled(state:MutableList<Boolean>){
        _buttonEnabled.value = state
    }

    fun getAuthEmail(email: String) {
        userEmail.value = email
        viewModelScope.launch(ceh) {
            repository.checkAuthEmail(email).let {
                authEmailState.value = it
            }
        }
    }

    fun findPassword(account:String, email:String, code:String){
        viewModelScope.launch(ceh) {
            repository.findPassword(account, email, code).let {
                _isSuccessCode.value = it
            }
        }
    }
}