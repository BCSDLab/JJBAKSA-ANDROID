package com.jjbaksa.jjbaksa.ui.findpassword

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordInputCodeBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

class FindPasswordInputCodeFragment : BaseFragment<FragmentFindPasswordInputCodeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password_input_code

    private val findPasswordViewModel: FindPasswordViewModel by activityViewModels()
    private var codeBoxState = mutableListOf<Boolean>(false, false, false, false)
    private lateinit var imm: InputMethodManager

    override fun initView() {
        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun initEvent() {
        reSendCode()
        nextToCodeBox()
        onClickCodeBoxLayout()
        observeData()
        onClickSuccessButton()
    }

    override fun subscribe() {}

    private fun reSendCode() {
        binding.textViewFindPasswordResendInputCode.setOnClickListener {
            Toast.makeText(requireContext(), "인증번호가 재발송 되었습니다.", Toast.LENGTH_SHORT).show()
            findPasswordViewModel.getAuthEmail(findPasswordViewModel.userEmail.value.toString())
        }
    }

    private fun nextToCodeBox() {
        with(binding) {
            editTextFindPasswordInputCodeOne.addTextChangedListener {
                onChangedCodeBoxState(editTextFindPasswordInputCodeOne, 0)
            }
            editTextFindPasswordInputCodeTwo.addTextChangedListener {
                onChangedCodeBoxState(editTextFindPasswordInputCodeTwo, 1)
            }
            editTextFindPasswordInputCodeThree.addTextChangedListener {
                onChangedCodeBoxState(editTextFindPasswordInputCodeThree, 2)
            }
            editTextFindPasswordInputCodeFour.addTextChangedListener {
                onChangedCodeBoxState(editTextFindPasswordInputCodeFour, 3)
            }
        }
    }

    private fun onChangedCodeBoxState(box: EditText, pos: Int) {
        when (pos) {
            0 -> {
                if (box.text.length == 1) {
                    binding.editTextFindPasswordInputCodeTwo.requestFocus()
                    codeBoxState[pos] = true
                } else codeBoxState[pos] = false
            }
            1 -> {
                if (box.text.length == 1) {
                    binding.editTextFindPasswordInputCodeThree.requestFocus()
                    codeBoxState[pos] = true
                } else codeBoxState[pos] = false
            }
            2 -> {
                if (box.text.length == 1) {
                    binding.editTextFindPasswordInputCodeFour.requestFocus()
                    codeBoxState[pos] = true
                } else codeBoxState[pos] = false
            }
            3 -> codeBoxState[pos] = box.text.length == 1
        }
        findPasswordViewModel.codeBoxState.value = codeBoxState
    }

    private fun onClickCodeBoxLayout() {
        binding.linearLayoutFindPasswordInputCodeLayout.setOnClickListener {
            with(binding) {
                editTextFindPasswordInputCodeOne.setText(null)
                editTextFindPasswordInputCodeTwo.setText(null)
                editTextFindPasswordInputCodeThree.setText(null)
                editTextFindPasswordInputCodeFour.setText(null)
                codeBoxState = mutableListOf(false, false, false, false)
                findPasswordViewModel.codeBoxState.value = codeBoxState
                editTextFindPasswordInputCodeOne.requestFocus()
                imm.showSoftInput(editTextFindPasswordInputCodeOne, InputMethodManager.SHOW_FORCED)
            }
        }
    }

    private fun onClickSuccessButton(){
        binding.buttonFindPasswordInputCode.setOnClickListener {
            findPasswordViewModel.getCodeNumber(
                binding.editTextFindPasswordInputCodeOne.text.toString(),
                binding.editTextFindPasswordInputCodeTwo.text.toString(),
                binding.editTextFindPasswordInputCodeThree.text.toString(),
                binding.editTextFindPasswordInputCodeFour.text.toString()
            )
            findPasswordViewModel.findPassword(
                "jonotch1",
                findPasswordViewModel.userEmail.value.toString(),
                findPasswordViewModel.codeNumber.value.toString()
            )
        }
    }

    private fun observeData() {
        findPasswordViewModel.codeBoxState.observe(
            viewLifecycleOwner,
            Observer<MutableList<Boolean>> {
                binding.buttonFindPasswordInputCode.isEnabled = !it.contains(false)
            }
        )
        findPasswordViewModel.isSuccessCode.observe(
            viewLifecycleOwner,
            Observer<Boolean>{
                if (it) {
                    findNavController().navigate(R.id.action_nav_find_password_input_code_to_nav_find_password_reset)
                } else {
                    binding.buttonFindPasswordInputCode.isEnabled = false
                    binding.layerFindPasswordWarningContentInInputCode.visibility = View.VISIBLE
                }
            }
        )
    }
}