package com.jjbaksa.jjbaksa.ui.setting.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : BaseViewModel() {
    fun logout() {
        viewModelScope.launch(ceh) {
            userUseCase.logout()
        }
    }
}
