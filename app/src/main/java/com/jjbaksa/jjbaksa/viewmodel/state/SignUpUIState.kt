package com.jjbaksa.jjbaksa.viewmodel.state

import com.jjbaksa.domain.enums.SignUpAlertEnum

data class SignUpUIState(
    val isIdChecked: Boolean = false,
    val isAlertShown: Boolean = false,
    val alertType: SignUpAlertEnum = SignUpAlertEnum.ID_EXIST
)
