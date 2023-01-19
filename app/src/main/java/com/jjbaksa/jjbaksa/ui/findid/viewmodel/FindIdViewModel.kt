package com.jjbaksa.jjbaksa.ui.findid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.FindIdRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val repository: FindIdRepository
) : BaseViewModel() {
    val userEmail = MutableLiveData<String>("")
    val userAccount = MutableLiveData<String>("")

    private val _authEmailState = SingleLiveEvent<RespResult<Boolean>>()
    val authEmailState: SingleLiveEvent<RespResult<Boolean>> get() = _authEmailState
    private val _numberBoxUiState = MutableLiveData<MutableList<Boolean>>()
    val numberBoxUiState: MutableLiveData<MutableList<Boolean>> get() = _numberBoxUiState

    fun updateBoxUiState(boxUiState: MutableList<Boolean>) {
        _numberBoxUiState.value = boxUiState
    }

    fun stateButton(emailLength: Int): Boolean {
        return emailLength > 0
    }

    fun getAuthEmail(email: String) {
        userEmail.value = email
        viewModelScope.launch(ceh) {
            repository.checkAuthEmail(email).let {
                authEmailState.value = it
            }
        }
    }

    fun getUserAccount(code: String) {
        viewModelScope.launch(ceh) {
            repository.findAccount(userEmail.value.toString(), code).let {
                userAccount.value = it
            }
        }
    }
}
