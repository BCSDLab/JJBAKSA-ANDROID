package com.jjbaksa.jjbaksa.ui.findpassword

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordInputCodeBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPasswordInputCodeFragment : BaseFragment<FragmentFindPasswordInputCodeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password_input_code

    private val viewModel: FindPasswordViewModel by activityViewModels()
    private val stateBox = mutableListOf<Boolean>(false, false, false, false)
    private val stateBoxNumber = mutableListOf<Int?>(null, null, null, null)
    private var outlineState = false

    override fun initView() {
        viewModel.setStateBox(stateBox)
        viewModel.setStateBoxNumber(stateBoxNumber)
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener {
            findNavController().popBackStack()
        }
        setEditText()
        completedResult()
        resendVerificationCode()
    }

    private fun setEditText() {
        binding.editTextContainer.addTextChangedListener { it, position ->
            if (outlineState) {
                binding.editTextContainer.setOutlineEditText(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_8
                    )
                )
                outlineState = false
            }
            if (it.isNullOrEmpty()) {
                stateBox[position!!] = false
                viewModel.setStateBox(stateBox)
                stateBoxNumber[position!!] = null
                viewModel.setStateBoxNumber(stateBoxNumber)
            } else {
                stateBox[position!!] = true
                viewModel.setStateBox(stateBox)
                stateBoxNumber[position!!] = it.toString().toInt()
                viewModel.setStateBoxNumber(stateBoxNumber)
            }
        }
    }

    private fun completedResult() {
        binding.completeButton.setOnClickListener {
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextContainer)
            if (viewModel.stateBoxNumber.value?.contains(null) == false) {
                viewModel.postUserPassword(stateBoxNumber.joinToString(""))
            }
        }
    }

    private fun resendVerificationCode() {
        binding.resendVerificationCodeTextView.setOnClickListener {
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextContainer)
            viewModel.postUserEmailPassword(
                viewModel.userId.value.toString(),
                viewModel.userEmail.value.toString()
            )
        }
    }

    override fun subscribe() {
        viewModel.stateBox.observe(viewLifecycleOwner) {
            if (!it.contains(false)) {
                binding.completeButton.isSelected = true
                binding.completeButton.isEnabled = true
            } else {
                binding.completeButton.isSelected = false
                binding.completeButton.isEnabled = false
            }
        }
        viewModel.toastMsg.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                showSnackBar(getString(R.string.resend_verification_code_content))
            } else {
                KeyboardProvider(requireContext()).hideKeyboard(binding.editTextContainer)
            }
        }
        viewModel.verifyResult.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_nav_find_password_input_code_to_nav_find_password_reset)
            } else {
                outlineState = true
                binding.editTextContainer.setOutlineEditText(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_8_stroke_ff7f23
                    )
                )
            }
        }
    }
}
