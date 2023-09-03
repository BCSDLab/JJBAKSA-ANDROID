package com.jjbaksa.jjbaksa.ui.findid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindIdViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : BaseViewModel() {
    val userEmail = MutableLiveData<String>("")

    private val _stateBox = SingleLiveEvent<MutableList<Boolean>>()
    val stateBox: SingleLiveEvent<MutableList<Boolean>> get() = _stateBox
    private val _stateBoxNumber = SingleLiveEvent<MutableList<Int?>>()
    val stateBoxNumber: SingleLiveEvent<MutableList<Int?>> get() = _stateBoxNumber

    private val _userEmailIdState = SingleLiveEvent<Boolean>()
    val userEmailIdState: SingleLiveEvent<Boolean> get() = _userEmailIdState

    private val _userId = SingleLiveEvent<String>()
    val userId: SingleLiveEvent<String> get() = _userId

    fun setStateBox(box: MutableList<Boolean>) {
        _stateBox.value = box
    }

    fun setStateBoxNumber(boxNumber: MutableList<Int?>) {
        _stateBoxNumber.value = boxNumber
    }

    fun stateButton(emailLength: Int): Boolean {
        return emailLength > 0
    }

    fun postUserEmailId(email: String) {
        userEmail.value = email
        viewModelScope.launch(ceh) {
            userUseCase.postUserEmailId(email) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _userEmailIdState.value = it
                }
            }
        }
    }

    fun getUserId(code: String) {
        viewModelScope.launch(ceh) {
            userUseCase.getUserId(userEmail.value.toString(), code) { errorMsg ->
                toastMsg.postValue(errorMsg)
            }.collect {
                it.onSuccess {
                    _userId.value = it
                }
            }
        }
    }
}
