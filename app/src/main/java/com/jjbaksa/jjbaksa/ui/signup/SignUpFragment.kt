package com.jjbaksa.jjbaksa.ui.signup

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputType
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.internal.TextWatcherAdapter
import com.jjbaksa.domain.enums.SignUpAlertEnum
import com.jjbaksa.domain.enums.SignUpAlertEnum.EMAIL_NOT_FOUND
import com.jjbaksa.domain.enums.SignUpAlertEnum.NEED_ID_CHECK
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_NOT_MATCH
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_RULE_NOT_MATCH
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentSignUpBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.ui.findpassword.FindPasswordResetFragment
import com.jjbaksa.jjbaksa.util.RegexUtil.isPasswordRuleMatch
import com.jjbaksa.jjbaksa.ui.signup.viewmodel.SignUpViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import com.jjbaksa.jjbaksa.util.RegexUtil.checkEmailFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_sign_up

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    private var isIdTyped = false
    private var isEmailTyped = false
    private var isPasswordTyped = false
    private var isPasswordConfirmedTyped = false
    private var isPasswordConfirmed = false
    private var passwordConfirmed = ""
    private var isEmailRuleMatch = false
    private var isPasswordRuleMatch = false
    private var alertExist = false

    private var id: String = ""
    private var email: String = ""
    override fun initView() {
    }

    override fun initEvent() {
        binding.jEditTextSignUpId.setTailButtonOnClickListener {
            signUpViewModel.id = id
            signUpViewModel.checkAccountAvailable(id) { isAvailable ->
                if (isAvailable) {
                    showSnackBar(getString(R.string.id_checked))
                    KeyboardProvider(requireContext()).hideKeyboard(binding.jEditTextSignUpId)
                    binding.jEditTextSignUpId.setEditTextAlertImg(false)
                } else {
                    binding.jEditTextSignUpId.setEditTextAlertImg(true)
                }
            }
        }

        binding.jEditTextSignUpId.addTextChangedListener(
            @SuppressLint("RestrictedApi")
            object : TextWatcherAdapter() {
                override fun afterTextChanged(p0: Editable) {
                    isIdTyped = p0?.isNotEmpty() == true
                    binding.jEditTextSignUpId.setTailButtonEnable(isIdTyped)
                    if (isIdTyped) {
                        id = p0.toString()
                    }
                    signUpViewModel.updateIdCheckedState(false)
                    updateSignUpNextButton(isIdTyped && isEmailTyped && isPasswordTyped && isPasswordConfirmedTyped)
                }
            }
        )

        binding.jEditTextSignUpEmail.addTextChangedListener(
            @SuppressLint("RestrictedApi")
            object : TextWatcherAdapter() {
                override fun afterTextChanged(p0: Editable) {
                    isEmailRuleMatch = checkEmailFormat(p0.toString())
                    isEmailTyped = p0.toString().isNotEmpty()
                    isIdTyped = p0?.isNotEmpty() == true
                    if (isEmailTyped) {
                        email = p0.toString()
                    }
                    if (isEmailRuleMatch) {
                        updateSignUpNextButton(isEmailTyped && isEmailRuleMatch)
                        if (signUpViewModel.uiState.value?.isAlertShown == true) {
                            signUpViewModel.updateAlertState(false)
                        }
                    } else {
                        signUpViewModel.updateAlertType(EMAIL_NOT_FOUND)
                        signUpViewModel.updateAlertState(true)
                    }
                    updateSignUpNextButton(isIdTyped && isEmailTyped && isPasswordTyped && isPasswordConfirmedTyped)
                }
            }
        )

        binding.jEditTextSignUpPassword.setTailImgOnClickListener {
            if (signUpViewModel.password.isNotEmpty()) {
                binding.jEditTextSignUpPassword.setTailImgSelected(!binding.jEditTextSignUpPassword.getTailImgSelected())

                if (binding.jEditTextSignUpPassword.getTailImgSelected()) {
                    binding.jEditTextSignUpPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                } else {
                    binding.jEditTextSignUpPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                }
            }
        }

        binding.jEditTextSignUpPassword.addTextChangedListener(
            @SuppressLint("RestrictedApi")
            object : TextWatcherAdapter() {
                override fun afterTextChanged(p0: Editable) {
                    isPasswordRuleMatch = p0.toString().isPasswordRuleMatch()
                    isPasswordTyped = p0.toString().isNotEmpty()

                    if (isPasswordTyped) {
                        signUpViewModel.password = p0.toString()
                        isPasswordConfirmed =
                            p0.toString() == passwordConfirmed
                    }
                    if (isPasswordRuleMatch) {
                        updateSignUpNextButton(isIdTyped && isEmailTyped && isPasswordTyped && isPasswordConfirmedTyped)

                        if (signUpViewModel.uiState.value?.isAlertShown == true) {
                            signUpViewModel.updateAlertState(false)
                        }
                    } else {
                        signUpViewModel.updateAlertType(PASSWORD_RULE_NOT_MATCH)
                        signUpViewModel.updateAlertState(true)
                    }
                }
            }
        )

        binding.jEditTextSignUpPasswordConfirm.setTailImgOnClickListener {
            if (isPasswordConfirmedTyped) {
                binding.jEditTextSignUpPasswordConfirm.setTailImgSelected(!binding.jEditTextSignUpPasswordConfirm.getTailImgSelected())

                if (binding.jEditTextSignUpPasswordConfirm.getTailImgSelected()) {
                    binding.jEditTextSignUpPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                } else {
                    binding.jEditTextSignUpPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                }
            }
        }

        binding.jEditTextSignUpPasswordConfirm.addTextChangedListener(
            @SuppressLint("RestrictedApi")
            object : TextWatcherAdapter() {
                override fun afterTextChanged(p0: Editable) {
                    isPasswordConfirmedTyped = p0.toString().isNotEmpty()
                    isPasswordConfirmed =
                        p0.toString() == signUpViewModel.password
                    passwordConfirmed = p0.toString()
                    if (!isPasswordConfirmed) {
                        signUpViewModel.updateAlertType(PASSWORD_NOT_MATCH)
                        signUpViewModel.updateAlertState(true)
                    } else {
                        signUpViewModel.updateAlertState(false)
                    }
                    updateSignUpNextButton(isIdTyped && isEmailTyped && isPasswordTyped && isPasswordConfirmedTyped)
                }
            }
        )

        binding.buttonSignUpNext.setOnClickListener {
            checkInputsCorrect()
            signUpViewModel.id = id
            signUpViewModel.email = email
            if (alertExist) {
                if (signUpViewModel.uiState.value?.isIdChecked == true) {

                    signUpViewModel.postUserEmailCheck(signUpViewModel.email)
                    ConfirmDialog(
                        getString(R.string.sign_up_title_text),
                        getString(R.string.sign_up_content_text),
                        getString(R.string.confirm),
                        {
                            findNavController().navigate(R.id.action_nav_graph_move_to_welcome)
                            dismissDialog()
                        }
                    ).show(parentFragmentManager, FindPasswordResetFragment.DIALOG_TAG)
                } else {
                    signUpViewModel.updateAlertType(NEED_ID_CHECK)
                    signUpViewModel.updateAlertState(true)
                }
            }
        }
    }

    private fun dismissDialog() {
        val dialogFragment = parentFragmentManager.findFragmentByTag(FindPasswordResetFragment.DIALOG_TAG)
        if (dialogFragment is DialogFragment) {
            dialogFragment.dismiss()
        }
    }
    override fun subscribe() {
        signUpViewModel.uiState.observe(viewLifecycleOwner) {
            binding.jEditTextSignUpId.setTailButtonEnable(!it.isIdChecked)
            setAlert(it.alertType)
        }

        signUpViewModel.userEmailCheckState.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_nav_graph_move_to_welcome)
            } else {
                binding.buttonSignUpNext.isEnabled = false
            }
        }

        signUpViewModel.toastMsg.observe(viewLifecycleOwner) {
            showSnackBar(it)
            KeyboardProvider(requireContext()).hideKeyboard(binding.jEditTextSignUpId)
        }
    }

    override fun onResume() {
        super.onResume()
        if (signUpViewModel.id.isNotEmpty()) {
            binding.jEditTextSignUpId.text = signUpViewModel.id
        }

        if (signUpViewModel.email.isNotEmpty()) {
            binding.jEditTextSignUpEmail.text = signUpViewModel.email
        }

        if (signUpViewModel.password.isNotEmpty()) {
            binding.jEditTextSignUpPassword.text = signUpViewModel.password
        }
    }

    private fun updateSignUpNextButton(isEnabled: Boolean) {
        binding.buttonSignUpNext.isEnabled = isEnabled
    }

    private fun setAlert(alertType: SignUpAlertEnum): String? {
        val alertMsg = when (alertType) {
            EMAIL_NOT_FOUND -> context?.getString(R.string.email_not_found)
            PASSWORD_RULE_NOT_MATCH -> context?.getString(R.string.password_rule_not_match)
            PASSWORD_NOT_MATCH -> context?.getString(R.string.password_not_match)
            NEED_ID_CHECK -> context?.getString(R.string.need_id_check)
        }

        return alertMsg
    }
    private fun checkInputsCorrect() {

        val uiState = signUpViewModel.uiState.value
        binding.jEditTextSignUpId.setEditTextAlertImg(false)
        binding.jEditTextSignUpEmail.setEditTextAlertImg(false)
        binding.jEditTextSignUpPassword.setEditTextAlertImg(false)
        binding.jEditTextSignUpPasswordConfirm.setEditTextAlertImg(false)

        alertExist = isPasswordConfirmed && isPasswordRuleMatch && isEmailRuleMatch

        if (alertExist) {
            binding.buttonSignUpNext.isEnabled = false
        }

        when {
            uiState?.isIdChecked != true -> {
                showSnackBar(setAlert(SignUpAlertEnum.NEED_ID_CHECK).toString())
                binding.jEditTextSignUpId.setEditTextAlertImg(signUpViewModel.uiState.value?.isIdChecked != true)
            }
            !isEmailRuleMatch -> {
                showSnackBar(setAlert(SignUpAlertEnum.EMAIL_NOT_FOUND).toString())
                binding.jEditTextSignUpEmail.setEditTextAlertImg(!isEmailRuleMatch)
            }
            !isPasswordRuleMatch -> {
                showSnackBar(setAlert(SignUpAlertEnum.PASSWORD_RULE_NOT_MATCH).toString())
                binding.jEditTextSignUpPassword.setEditTextAlertImg(!isPasswordRuleMatch)
            }
            !isPasswordConfirmed ->
                {
                    showSnackBar(setAlert(SignUpAlertEnum.PASSWORD_NOT_MATCH).toString())
                    binding.jEditTextSignUpPasswordConfirm.setEditTextAlertImg(!isPasswordConfirmed)
                }
        }
    }
}
