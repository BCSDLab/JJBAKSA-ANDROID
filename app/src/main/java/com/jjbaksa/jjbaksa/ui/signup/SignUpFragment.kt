package com.jjbaksa.jjbaksa.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.enums.SignUpAlertEnum
import com.jjbaksa.domain.enums.SignUpAlertEnum.EMAIL_NOT_FOUND
import com.jjbaksa.domain.enums.SignUpAlertEnum.ID_EXIST
import com.jjbaksa.domain.enums.SignUpAlertEnum.NEED_ID_CHECK
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_NOT_MATCH
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_RULE_NOT_MATCH
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentSignUpBinding
import com.jjbaksa.jjbaksa.util.RegexUtil.isPasswordRuleMatch
import com.jjbaksa.jjbaksa.ui.signup.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    private var isIdTyped = false
    private var isEmailTyped = false
    private var isPasswordTyped = false
    private var isPasswordConfirmed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_up, container, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signUpViewModel.uiState.collect {
                    binding.jjEditTextSignUpId.isButtonEnabled = !it.isIdChecked
                    setAlert(it.alertType)
                    binding.textViewSignUpAlert.visibility =
                        if (it.isAlertShown) View.VISIBLE else View.INVISIBLE
                    binding.imageViewSignUpAlert.visibility =
                        if (it.isAlertShown) View.VISIBLE else View.INVISIBLE
                }
            }
        }

        binding.jjEditTextSignUpId.setOnClickListener {
            signUpViewModel.checkAccountAvailable(
                binding.jjEditTextSignUpId.editTextText
            )

            signUpViewModel.isIdAvailable.observe(viewLifecycleOwner) {
                if (it) {
                    signUpViewModel.updateIdCheckedState(true)
                    signUpViewModel.updateAlertState(false)
                } else {
                    signUpViewModel.updateAlertType(ID_EXIST)
                    signUpViewModel.updateAlertState(true)
                }
            }
        }

        binding.jjEditTextSignUpId.addTextChangedListener {
            // Add check id logic here
            isIdTyped = it.toString().isNotEmpty()
            if (isIdTyped) {
                signUpViewModel.id = it.toString()
            }
            updateSignUpNextButton(isIdTyped)
            if (signUpViewModel.isTypedIdChanged()) {
                // Enable button again if id modified
                binding.jjEditTextSignUpId.isButtonEnabled = true
                // Set Id Checked state to false if id modified
                signUpViewModel.updateIdCheckedState(false)
            }
        }

        binding.jjEditTextSignUpId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isIdTyped)
            }
        }

        binding.jjEditTextSignUpEmail.addTextChangedListener {
            // Add check email logic here
            isEmailTyped = it.toString().isNotEmpty()
            if (isEmailTyped) {
                signUpViewModel.email = it.toString()
            }
            updateSignUpNextButton(isEmailTyped)
        }

        binding.jjEditTextSignUpEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isEmailTyped)
            }
        }

        binding.jjEditTextSignUpPassword.addTextChangedListener {
            val isPasswordRuleMatch = it.toString().isPasswordRuleMatch()
            isPasswordTyped = it.toString().isNotEmpty()

            if (isPasswordTyped) {
                signUpViewModel.password = it.toString()
            }

            if (it.toString().isPasswordRuleMatch()) {
                updateSignUpNextButton(isPasswordTyped && isPasswordRuleMatch)
                if (signUpViewModel.uiState.value.isAlertShown) {
                    signUpViewModel.updateAlertState(false)
                }
            } else {
                signUpViewModel.updateAlertType(PASSWORD_RULE_NOT_MATCH)
                signUpViewModel.updateAlertState(true)
            }
        }

        binding.jjEditTextSignUpPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isPasswordTyped)
            }
        }

        binding.jjEditTextSignUpPasswordConfirm.addTextChangedListener {
            isPasswordConfirmed =
                it.toString() == binding.jjEditTextSignUpPassword.editTextText
            if (!isPasswordConfirmed) {
                signUpViewModel.updateAlertType(PASSWORD_NOT_MATCH)
                signUpViewModel.updateAlertState(true)
            } else {
                signUpViewModel.updateAlertState(false)
            }
            updateSignUpNextButton(isPasswordConfirmed)
        }

        binding.jjEditTextSignUpPasswordConfirm.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isPasswordConfirmed)
            }
        }

        binding.buttonSignUpNext.setOnClickListener {
            if (isPasswordConfirmed) {
                if (signUpViewModel.uiState.value.isIdChecked) {
                    findNavController().navigate(R.id.action_nav_graph_move_to_welcome)
                } else {
                    signUpViewModel.updateAlertType(NEED_ID_CHECK)
                    signUpViewModel.updateAlertState(true)
                }
            } else if (isPasswordTyped) {
                binding.jjEditTextSignUpPasswordConfirm.requestFocus()
            } else if (isEmailTyped) {
                binding.jjEditTextSignUpPassword.requestFocus()
            } else if (isIdTyped) {
                binding.jjEditTextSignUpEmail.requestFocus()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (signUpViewModel.id.isNotEmpty()) {
            binding.jjEditTextSignUpId.editTextText = signUpViewModel.id
        }

        if (signUpViewModel.email.isNotEmpty()) {
            binding.jjEditTextSignUpEmail.editTextText = signUpViewModel.email
        }

        if (signUpViewModel.password.isNotEmpty()) {
            binding.jjEditTextSignUpPassword.editTextText = signUpViewModel.password
        }
    }

    private fun updateSignUpNextButton(isEnabled: Boolean) {
        binding.buttonSignUpNext.isEnabled = isEnabled
    }

    private fun setAlert(alertType: SignUpAlertEnum) {
        binding.textViewSignUpAlert.text = when (alertType) {
            ID_EXIST -> context?.getString(R.string.id_already_exist)
            EMAIL_NOT_FOUND -> context?.getString(R.string.email_not_found)
            PASSWORD_RULE_NOT_MATCH -> context?.getString(R.string.password_rule_not_match)
            PASSWORD_NOT_MATCH -> context?.getString(R.string.password_not_match)
            NEED_ID_CHECK -> context?.getString(R.string.need_id_check)
        }
    }
}
