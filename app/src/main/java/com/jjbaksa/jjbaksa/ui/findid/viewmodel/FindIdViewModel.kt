package com.jjbaksa.jjbaksa.ui.findid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    val userEmail = MutableLiveData<String>("")

    private val _stateBox = SingleLiveEvent<MutableList<Boolean>>()
    val stateBox: SingleLiveEvent<MutableList<Boolean>> get() = _stateBox
    private val _stateBoxNumber = SingleLiveEvent<MutableList<Int?>>()
    val stateBoxNumber: SingleLiveEvent<MutableList<Int?>> get() = _stateBoxNumber

    private val _authEmailState = SingleLiveEvent<FormatResp>()
    val authEmailState: SingleLiveEvent<FormatResp> get() = _authEmailState

    private val _userIdInfo = SingleLiveEvent<FormatResp>()
    val userIdInfo: SingleLiveEvent<FormatResp> get() = _userIdInfo

    fun setStateBox(box: MutableList<Boolean>) {
        _stateBox.value = box
    }

    fun setStateBoxNumber(boxNumber: MutableList<Int?>) {
        _stateBoxNumber.value = boxNumber
    }

    fun stateButton(emailLength: Int): Boolean {
        return emailLength > 0
    }

    fun getAuthEmail(email: String) {
        userEmail.value = email
        viewModelScope.launch(ceh) {
            repository.checkAuthEmail(email).let {
                _authEmailState.value = it
            }
        }
    }

    fun getUserAccount(code: String) {
        viewModelScope.launch(ceh) {
            repository.findAccount(userEmail.value.toString(), code).let {
                _userIdInfo.value = it
            }
        }
    }
}
