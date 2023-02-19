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
import javax.inject.Inject

@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    val userEmail = MutableLiveData<String>("")
    val codeBoxState = MutableLiveData(mutableListOf(false, false, false, false))

    private val _authEmailState = SingleLiveEvent<RespResult<Boolean>>()
    val authEmailState: SingleLiveEvent<RespResult<Boolean>> get() = _authEmailState

    fun getAuthEmail(email: String) {
        userEmail.value = email
        viewModelScope.launch(ceh) {
            repository.checkAuthEmail(email).let {
                authEmailState.value = it
            }
        }
    }
}