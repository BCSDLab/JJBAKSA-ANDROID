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
}