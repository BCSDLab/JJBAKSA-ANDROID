package com.jjbaksa.jjbaksa.ui.findpassword

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordInputCodeBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.RegexUtil

class FindPasswordInputCodeFragment : BaseFragment<FragmentFindPasswordInputCodeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password_input_code

    private val findPasswordViewModel: FindPasswordViewModel by activityViewModels()
    private lateinit var imm: InputMethodManager

    private var isSendVerificationCode = false

    override fun initView() {
        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun initEvent() {
        observeData()
        onClickButton()
        onEnabledButtonState()
    }

    override fun subscribe() {}

    override fun onStart() {
        super.onStart()
        with(binding) {
            editTextFindPasswordInputEmail.setText(null)
            editTextFindPasswordInputVerificationCode.setText(null)
        }
    }

    private fun onEnabledButtonState() {
        binding.editTextFindPasswordInputEmail.addTextChangedListener {
            binding.buttonSendVerificationCode.isEnabled = it?.length!! > 0
        }
        binding.editTextFindPasswordInputVerificationCode.addTextChangedListener {
            binding.buttonFindPasswordInputCode.isEnabled =
                it?.length!! > 0 && isSendVerificationCode
        }
    }

    private fun onClickButton() {
        binding.buttonSendVerificationCode.setOnClickListener {
            if (isSendVerificationCode) {
                findPasswordViewModel.getAuthEmail(binding.editTextFindPasswordInputEmail.text.toString())
            } else {
                with(binding.editTextFindPasswordInputEmail.text.toString()) {
                    if (RegexUtil.checkEmailFormat(this)) {
                        findPasswordViewModel.getAuthEmail(this)
                    } else {
                        failEmailCheck(
                            getString(R.string.not_correct_email_format_and_not_exist_account),
                            binding.buttonSendVerificationCode,
                            binding.editTextFindPasswordInputEmail
                        )
                    }
                }
            }
        }
        binding.buttonFindPasswordInputCode.setOnClickListener {
            findPasswordViewModel.findPassword(
                findPasswordViewModel.userAccount.value.toString(),
                binding.editTextFindPasswordInputEmail.text.toString(),
                binding.editTextFindPasswordInputVerificationCode.text.toString()
            )
        }
    }

    private fun failEmailCheck(msg: String, button: Button, editText: EditText) {
        binding.textViewFindPasswordNotCorrectEmailFormatAndVerificationCode.text = msg
        binding.layerFindPasswordWarningContentInInputCode.visibility = View.VISIBLE
        button.isEnabled = false
        editText.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
    }

    private fun successEmailCheck() {
        binding.layerFindPasswordWarningContentInInputCode.visibility = View.INVISIBLE
        binding.editTextFindPasswordInputEmail.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8)
        binding.editTextFindPasswordInputVerificationCode.requestFocus()
        binding.buttonSendVerificationCode.text = getString(R.string.resend_verification_code)
    }

    private fun onToastReSendVerificationCode() {
        if (isSendVerificationCode) {
            binding.buttonSendVerificationCode.isEnabled = false
            binding.editTextFindPasswordInputVerificationCode.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8)
            Toast.makeText(
                context,
                getString(R.string.resend_verification_code),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeData() {
        findPasswordViewModel.userToken.observe(
            viewLifecycleOwner,
            Observer<String?> {
                if (it != null) {
                    when (it) {
                        ERROR_MSG_EMAIL -> {
                            failEmailCheck(
                                getString(R.string.not_correct_email_format_and_not_exist_account),
                                binding.buttonSendVerificationCode,
                                binding.editTextFindPasswordInputEmail
                            )
                            binding.editTextFindPasswordInputVerificationCode.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8)
                            binding.editTextFindPasswordInputVerificationCode.requestFocus()
                        }
                        ERROR_MSG_VERIFICATION_CODE -> {
                            failEmailCheck(
                                getString(R.string.not_correct_verification_code),
                                binding.buttonFindPasswordInputCode,
                                binding.editTextFindPasswordInputVerificationCode
                            )
                            binding.editTextFindPasswordInputEmail.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8)
                            binding.editTextFindPasswordInputEmail.requestFocus()
                        }
                        else -> {
                            findNavController().navigate(R.id.action_nav_find_password_input_code_to_nav_find_password_reset)
                        }
                    }
                } else {
                    failEmailCheck(
                        getString(R.string.not_correct_verification_code),
                        binding.buttonFindPasswordInputCode,
                        binding.editTextFindPasswordInputVerificationCode
                    )
                }
            }
        )
        findPasswordViewModel.authEmailState.observe(
            viewLifecycleOwner,
            Observer<RespResult<Boolean>> {
                if (it == RespResult.Success(true)) {
                    onToastReSendVerificationCode()
                    isSendVerificationCode = true
                    successEmailCheck()
                } else {
                    if (it == RespResult.Error<ErrorType>(ErrorType(ERROR_MSG_38, CODE_38))) {
                        binding.textViewFindPasswordNotCorrectEmailFormatAndVerificationCode.text =
                            "잠시 후 시도해주세요."
                        binding.layerFindPasswordWarningContentInInputCode.visibility = View.VISIBLE
                    } else {
                        failEmailCheck(
                            getString(R.string.not_correct_email_format_and_not_exist_account),
                            binding.buttonSendVerificationCode,
                            binding.editTextFindPasswordInputEmail
                        )
                    }
                }
            }
        )
    }

    companion object {
        const val ERROR_MSG_EMAIL = "계정이 존재하지 않거나 이메일 형식이 올바르지 않습니다."
        const val ERROR_MSG_VERIFICATION_CODE = "인증 번호가 올바르지 않습니다."
        const val ERROR_MSG_38 = "이메일 전송 횟수가 초과되었습니다."
        const val CODE_38 = 38
    }
}
