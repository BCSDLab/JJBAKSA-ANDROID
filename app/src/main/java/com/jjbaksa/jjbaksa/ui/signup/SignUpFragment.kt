package com.jjbaksa.jjbaksa.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.enums.SignUpAlertEnum
import com.jjbaksa.domain.enums.SignUpAlertEnum.EMAIL_NOT_FOUND
import com.jjbaksa.domain.enums.SignUpAlertEnum.ID_EXIST
import com.jjbaksa.domain.enums.SignUpAlertEnum.NEED_ID_CHECK
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_NOT_MATCH
import com.jjbaksa.domain.enums.SignUpAlertEnum.PASSWORD_RULE_NOT_MATCH
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

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

        binding.jjEditTextSignUpId.setOnClickListener {
            // Add id check logic here
        }

        binding.jjEditTextSignUpId.addTextChangedListener {
            // Add check id logic here
            isIdTyped = it.toString().isNotEmpty()
            isTypeCompleted()
        }

        binding.jjEditTextSignUpEmail.addTextChangedListener {
            // Add check email logic here
            isEmailTyped = it.toString().isNotEmpty()
            isTypeCompleted()
        }

        binding.jjEditTextSignUpPassword.addTextChangedListener {
            // Add check password logic here
            isPasswordTyped = it.toString().isNotEmpty()
            isTypeCompleted()
        }

        binding.jjEditTextSignUpPasswordConfirm.addTextChangedListener {
            isPasswordConfirmed =
                it.toString() == binding.jjEditTextSignUpPassword.getText().toString()
            if (!isPasswordConfirmed)
                showAlert(PASSWORD_NOT_MATCH)
            else
                hideAlert()
            isTypeCompleted()
        }

        binding.buttonSignUpNext.setOnClickListener {
            findNavController().navigate(R.id.action_nav_graph_move_to_welcome)
        }

        return binding.root
    }

    private fun isTypeCompleted() {
        binding.buttonSignUpNext.isEnabled =
            isIdTyped && isEmailTyped && isPasswordTyped && isPasswordConfirmed
    }

    private fun showAlert(alertType: SignUpAlertEnum) {
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
        binding.textViewSignUpAlert.visibility = View.INVISIBLE
        binding.imageViewSignUpAlert.visibility = View.INVISIBLE
    }
}
