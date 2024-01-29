package com.jjbaksa.jjbaksa.ui.signup

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputType
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.internal.TextWatcherAdapter
import com.jjbaksa.domain.enums.SignUpAlertEnum
import com.jjbaksa.domain.enums.SignUpAlertEnum.EMAIL_ALREADY_EXIST
import com.jjbaksa.domain.enums.SignUpAlertEnum.EMAIL_NOT_FOUND
import com.jjbaksa.domain.enums.SignUpAlertEnum.NEED_ID_CHECK
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_NOT_MATCH
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_RULE_NOT_MATCH
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentSignUpBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.util.RegexUtil.isPasswordRuleMatch
import com.jjbaksa.jjbaksa.ui.signup.viewmodel.SignUpViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import com.jjbaksa.jjbaksa.util.RegexUtil
import com.jjbaksa.jjbaksa.util.RegexUtil.checkEmailFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_sign_up

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    private var isPasswordConfirmed = false
    private var isPasswordRuleMatch = false
    private var nextButtonEnable = false

    override fun initView() {
    }

    override fun initEvent() {
        binding.jEditTextSignUpId.setTailButtonOnClickListener {
            if (!RegexUtil.checkIdFormat(signUpViewModel.id)) {
                showSnackBar(getString(R.string.id_format_not_match))
                KeyboardProvider(requireContext()).hideKeyboard(binding.jEditTextSignUpId)
                return@setTailButtonOnClickListener
            }
            signUpViewModel.checkAccountAvailable(signUpViewModel.id) { isAvailable ->
                if (isAvailable) {
                    binding.buttonSignUpNext.isEnabled = true
                    showSnackBar(getString(R.string.id_checked))
                    KeyboardProvider(requireContext()).hideKeyboard(binding.jEditTextSignUpId)
                    binding.jEditTextSignUpId.setEditTextAlertImg(false)
                } else {
                    binding.buttonSignUpNext.isEnabled = false
                    showSnackBar(getString(R.string.id_already_exist))
                    KeyboardProvider(requireContext()).hideKeyboard(binding.jEditTextSignUpId)
                    binding.jEditTextSignUpId.setEditTextAlertImg(true)
                }
            }
        }

        binding.jEditTextSignUpId.addTextChangedListener(
            @SuppressLint("RestrictedApi")
            object : TextWatcherAdapter() {
                override fun afterTextChanged(p0: Editable) {
                    nextButtonEnable = p0?.isNotEmpty() == true
                    binding.jEditTextSignUpId.setTailButtonEnable(p0?.isNotEmpty() == true)
                    if (p0?.isNotEmpty() == true) {
                        signUpViewModel.id = p0.toString()
                    }
                    signUpViewModel.updateIdCheckedState(false)
                }
            }
        )

        binding.jEditTextSignUpEmail.addTextChangedListener(
            @SuppressLint("RestrictedApi")
            object : TextWatcherAdapter() {
                override fun afterTextChanged(p0: Editable) {
                    nextButtonEnable = p0?.isNotEmpty() == true
                    if (p0?.isNotEmpty() == true) {
                        signUpViewModel.email = p0.toString()
                    }
                    if (checkEmailFormat(p0.toString())) {
                        updateSignUpNextButton(signUpViewModel.email.isNotEmpty() && checkEmailFormat(p0.toString()))
                        if (signUpViewModel.uiState.value?.isAlertShown == true) {
                            signUpViewModel.updateAlertState(false)
                        }
                    } else {
                        signUpViewModel.updateAlertType(EMAIL_NOT_FOUND)
                        signUpViewModel.updateAlertState(true)
                    }
                    updateSignUpNextButton(signUpViewModel.id.isNotEmpty() && signUpViewModel.email.isNotEmpty() && signUpViewModel.password.isNotEmpty())
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
                    nextButtonEnable = p0?.isNotEmpty() == true
                    isPasswordRuleMatch = p0.toString().isPasswordRuleMatch()

                    if (p0.toString().isNotEmpty()) {
                        signUpViewModel.password = p0.toString()
                        isPasswordConfirmed =
                            p0.toString() == signUpViewModel.passwordCheck
                    }
                    if (isPasswordRuleMatch) {
                        updateSignUpNextButton(signUpViewModel.id.isNotEmpty() && signUpViewModel.email.isNotEmpty() && signUpViewModel.password.isNotEmpty())

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

            binding.jEditTextSignUpPasswordConfirm.setTailImgSelected(!binding.jEditTextSignUpPasswordConfirm.getTailImgSelected())

            if (binding.jEditTextSignUpPasswordConfirm.getTailImgSelected()) {
                binding.jEditTextSignUpPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            } else {
                binding.jEditTextSignUpPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        }

        binding.jEditTextSignUpPasswordConfirm.addTextChangedListener(
            @SuppressLint("RestrictedApi")
            object : TextWatcherAdapter() {
                override fun afterTextChanged(p0: Editable) {
                    nextButtonEnable = p0?.isNotEmpty() == true
                    isPasswordConfirmed =
                        p0.toString() == signUpViewModel.password
                    signUpViewModel.passwordCheck = p0.toString()
                    if (!isPasswordConfirmed) {
                        signUpViewModel.updateAlertType(PASSWORD_NOT_MATCH)
                        signUpViewModel.updateAlertState(true)
                    } else {
                        signUpViewModel.updateAlertState(false)
                    }
                    updateSignUpNextButton(signUpViewModel.id.isNotEmpty() && signUpViewModel.email.isNotEmpty() && signUpViewModel.password.isNotEmpty())
                }
            }
        )

        binding.buttonSignUpNext.setOnClickListener {
            checkInputsCorrect()
            if (!signUpViewModel.uiState.value?.isAlertShown!!) {
                signUpViewModel.signUpRequest(
                    {
                        if (signUpViewModel.uiState.value?.isIdChecked == true && signUpViewModel.isSignUpSuccess) {
                            signUpViewModel.postUserEmailCheck(signUpViewModel.email)
                            ConfirmDialog(
                                getString(R.string.sign_up_title_text),
                                getString(R.string.sign_up_content_text),
                                getString(R.string.confirm),
                                {
                                    findNavController().navigate(R.id.action_nav_graph_move_to_welcome)
                                    dismissDialog()
                                }
                            ).show(parentFragmentManager, DIALOG_TAG)
                        } else {
                            signUpViewModel.updateAlertType(NEED_ID_CHECK)
                            signUpViewModel.updateAlertState(true)
                        }
                    }
                ) {
                    KeyboardProvider(requireContext()).run {
                        hideKeyboard(binding.jEditTextSignUpId)
                        hideKeyboard(binding.jEditTextSignUpEmail)
                        hideKeyboard(binding.jEditTextSignUpPassword)
                        hideKeyboard(binding.jEditTextSignUpPasswordConfirm)
                    }
                    showSnackBar(it)
                    signUpViewModel.updateAlertState(true)
                    signUpViewModel.isSignUpSuccess = false
                }
            }
        }
    }

    private fun dismissDialog() {
        val dialogFragment = parentFragmentManager.findFragmentByTag(DIALOG_TAG)
        if (dialogFragment is DialogFragment) {
            dialogFragment.dismiss()
        }
    }
    override fun subscribe() {
        signUpViewModel.uiState.observe(viewLifecycleOwner) {
            binding.jEditTextSignUpId.setTailButtonEnable(!it.isIdChecked)
            setAlert(it.alertType)
        }

        signUpViewModel.login.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
            } else {
                showSnackBar(it.errorMessage)
            }
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
            EMAIL_ALREADY_EXIST -> context?.getString(R.string.email_already_exist)
        }

        return alertMsg
    }
    private fun checkInputsCorrect() {

        val uiState = signUpViewModel.uiState.value
        binding.jEditTextSignUpId.setEditTextAlertImg(false)
        binding.jEditTextSignUpEmail.setEditTextAlertImg(false)
        binding.jEditTextSignUpPassword.setEditTextAlertImg(false)
        binding.jEditTextSignUpPasswordConfirm.setEditTextAlertImg(false)

        signUpViewModel.updateAlertState(!isPasswordConfirmed || !isPasswordRuleMatch || !checkEmailFormat(signUpViewModel.email))

        if (signUpViewModel.uiState.value?.isAlertShown == true) {
            binding.buttonSignUpNext.isEnabled = false
        }

        when {
            uiState?.isIdChecked != true -> {
                showSnackBar(setAlert(SignUpAlertEnum.NEED_ID_CHECK).toString())
                binding.jEditTextSignUpId.setEditTextAlertImg(signUpViewModel.uiState.value?.isIdChecked != true)
            }
            !checkEmailFormat(signUpViewModel.email) -> {
                showSnackBar(setAlert(SignUpAlertEnum.EMAIL_NOT_FOUND).toString())
                binding.jEditTextSignUpEmail.setEditTextAlertImg(!checkEmailFormat(signUpViewModel.email))
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

    companion object {
        private const val DIALOG_TAG = "DIALOG_TAG"
    }
}
