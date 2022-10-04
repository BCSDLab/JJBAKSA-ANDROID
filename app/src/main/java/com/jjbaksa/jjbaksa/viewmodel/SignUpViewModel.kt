package com.jjbaksa.jjbaksa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.usecase.CheckIdAvailableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val checkIdAvailableUseCase: CheckIdAvailableUseCase) :
    ViewModel() {

    private val _isIdAvailable = MutableLiveData<Boolean>()
    val isIdAvailable: LiveData<Boolean>
        get() = _isIdAvailable

    private lateinit var id: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var nickname: String

    fun checkAccountAvailable(account: String) {
        viewModelScope.launch {
            runCatching {
                checkIdAvailableUseCase(account)
            }.onSuccess { result ->
                _isIdAvailable.value = result
            }.onFailure {
                // Handle error here
            }
        }
    }

    fun submitIdPasswordEmail(id: String, password: String, email: String){
        this.id = id
        this.password = password
        this.email = email
    }

    fun submitNickname(nickname: String){
        this.nickname = nickname
    }
}