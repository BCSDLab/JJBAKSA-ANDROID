package com.jjbaksa.jjbaksa.ui.signup.viewmodel.state

import com.jjbaksa.domain.enums.SignUpAlertEnum

data class SignUpUIState(
    val isIdChecked: Boolean = false,
    val isAlertShown: Boolean = false,
    var alertType: SignUpAlertEnum = SignUpAlertEnum.EMAIL_NOT_FOUND
)
