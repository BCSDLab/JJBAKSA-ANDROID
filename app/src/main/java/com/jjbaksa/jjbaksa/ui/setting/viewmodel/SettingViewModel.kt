package com.jjbaksa.jjbaksa.ui.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    fun signOut() {
        viewModelScope.launch(ceh) {
            repository.singOut()
        }
    }
}
