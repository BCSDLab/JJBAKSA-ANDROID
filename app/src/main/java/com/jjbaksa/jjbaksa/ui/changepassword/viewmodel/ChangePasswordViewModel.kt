package com.jjbaksa.jjbaksa.ui.changepassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {
    fun checkPassword(password: String) {
        viewModelScope.launch {
            repository.checkPassword(password)
        }
    }
}
