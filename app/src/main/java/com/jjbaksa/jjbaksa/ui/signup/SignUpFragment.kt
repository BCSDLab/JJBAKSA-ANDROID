package com.jjbaksa.jjbaksa.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.enums.SignUpAlertEnum
import com.jjbaksa.domain.enums.SignUpAlertEnum.EMAIL_NOT_FOUND
import com.jjbaksa.domain.enums.SignUpAlertEnum.ID_EXIST
import com.jjbaksa.domain.enums.SignUpAlertEnum.NEED_ID_CHECK
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_NOT_MATCH
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_RULE_NOT_MATCH
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentSignUpBinding
import com.jjbaksa.jjbaksa.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    private var isIdChecked = false
    private var isIdTyped = false
    private var isEmailTyped = false
    private var isPasswordTyped = false
    private var isPasswordConfirmed = false
    private var isAlertShown = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_up, container, false)

        binding.jjEditTextSignUpId.setOnClickListener {
            if (!isIdChecked) {
                signUpViewModel.checkAccountAvailable(binding.jjEditTextSignUpId.getText().toString())

                signUpViewModel.isIdAvailable.observe(viewLifecycleOwner) {
                    if (it) {
                        binding.jjEditTextSignUpId.updateButtonStyle(true)
                        isIdChecked = true
                        if (isAlertShown) {
                            hideAlert()
                        }
                    } else {
                        showAlert(ID_EXIST)
                    }
                }
            }
        }

        binding.jjEditTextSignUpId.addTextChangedListener {
            // Add check id logic here
            isIdTyped = it.toString().isNotEmpty()
            updateSignUpNextButton(isIdTyped)
        }

        binding.jjEditTextSignUpId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isIdTyped)
            }
        }

        binding.jjEditTextSignUpEmail.addTextChangedListener {
            // Add check email logic here
            isEmailTyped = it.toString().isNotEmpty()
            updateSignUpNextButton(isEmailTyped)
        }

        binding.jjEditTextSignUpEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isEmailTyped)
            }
        }

        binding.jjEditTextSignUpPassword.addTextChangedListener {
            // Add check password logic here
            isPasswordTyped = it.toString().isNotEmpty()
            updateSignUpNextButton(isPasswordTyped)
        }

        binding.jjEditTextSignUpPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isPasswordTyped)
            }
        }

        binding.jjEditTextSignUpPasswordConfirm.addTextChangedListener {
            isPasswordConfirmed =
                it.toString() == binding.jjEditTextSignUpPassword.getText().toString()
            if (!isPasswordConfirmed)
                showAlert(PASSWORD_NOT_MATCH)
            else
                hideAlert()
            updateSignUpNextButton(isPasswordConfirmed)
        }

        binding.jjEditTextSignUpPasswordConfirm.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateSignUpNextButton(isPasswordConfirmed)
            }
        }

        binding.buttonSignUpNext.setOnClickListener {
            if (isPasswordConfirmed) {
                if (isIdChecked) {
                    signUpViewModel.submitIdPasswordEmail(
                        binding.jjEditTextSignUpId.getText().toString(),
                        binding.jjEditTextSignUpPassword.getText().toString(),
                        binding.jjEditTextSignUpEmail.getText().toString()
                    )

                    findNavController().navigate(R.id.action_nav_graph_move_to_welcome)
                } else {
                    showAlert(NEED_ID_CHECK)
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

    private fun updateSignUpNextButton(isEnabled: Boolean) {
        binding.buttonSignUpNext.isEnabled = isEnabled
    }

    private fun showAlert(alertType: SignUpAlertEnum) {
        isAlertShown = true
        binding.textViewSignUpAlert.visibility = View.VISIBLE
        binding.imageViewSignUpAlert.visibility = View.VISIBLE
        binding.textViewSignUpAlert.text = when (alertType) {
            ID_EXIST -> context?.getString(R.string.id_already_exist)
            EMAIL_NOT_FOUND -> context?.getString(R.string.email_not_found)
            PASSWORD_RULE_NOT_MATCH -> context?.getString(R.string.password_rule_not_match)
            PASSWORD_NOT_MATCH -> context?.getString(R.string.password_not_match)
            NEED_ID_CHECK -> context?.getString(R.string.need_id_check)
        }
    }

    private fun hideAlert() {
        isAlertShown = false
        binding.textViewSignUpAlert.visibility = View.INVISIBLE
        binding.imageViewSignUpAlert.visibility = View.INVISIBLE
    }
}
