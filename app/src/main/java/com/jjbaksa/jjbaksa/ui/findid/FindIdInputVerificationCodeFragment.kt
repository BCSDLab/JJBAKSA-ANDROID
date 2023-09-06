package com.jjbaksa.jjbaksa.ui.findid

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdInputVerificationCodeBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.ui.findid.viewmodel.FindIdViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindIdInputVerificationCodeFragment :
    BaseFragment<FragmentFindIdInputVerificationCodeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_id_input_verification_code

    private val viewModel: FindIdViewModel by activityViewModels()

    private val stateBox = mutableListOf<Boolean>(false, false, false, false)
    private val stateBoxNumber = mutableListOf<Int?>(null, null, null, null)
    private var outlineState = false

    override fun initView() {
        val ft = parentFragmentManager.beginTransaction()
        val prev = parentFragmentManager.findFragmentByTag(FIND_ID_DIALOG_TAG)
        if (prev != null) ft.remove(prev)
        ft.addToBackStack(null)

        viewModel.setStateBox(stateBox)
        viewModel.setStateBoxNumber(stateBoxNumber)
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener {
            findNavController().popBackStack()
        }
        setEditText()
        reSendVerificationCode()
        findId()
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

    private fun reSendVerificationCode() {
        binding.textViewFindIdResendVerificationCode.setOnClickListener {
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextContainer)
            viewModel.postUserEmailId(viewModel.userEmail.value.toString())
        }
    }

    private fun findId() {
        binding.buttonFindIdVerificationCode.setOnClickListener {
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextContainer)
            if (viewModel.stateBoxNumber.value?.contains(null) == false) {
                viewModel.getUserId(stateBoxNumber.joinToString(""))
            }
        }
    }

    override fun subscribe() {
        viewModel.userEmailIdState.observe(
            viewLifecycleOwner
        ) {
            if (it) showSnackBar(getString(R.string.resend_verification_code_content))
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextContainer)
        }
        viewModel.toastMsg.observe(viewLifecycleOwner) {
            showSnackBar(it)
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextContainer)
        }
        viewModel.stateBox.observe(
            viewLifecycleOwner
        ) {
            if (!it.contains(false)) {
                binding.buttonFindIdVerificationCode.isSelected = true
                binding.buttonFindIdVerificationCode.isEnabled = true
            } else {
                binding.buttonFindIdVerificationCode.isSelected = false
                binding.buttonFindIdVerificationCode.isEnabled = false
            }
        }
        viewModel.userId.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                ConfirmDialog(
                    getString(R.string.success_find_account),
                    "${viewModel.userEmail.value}으로 가입된 아이디는 ${it}입니다.",
                    getString(R.string.complete),
                    { activity?.finish() }
                ).show(parentFragmentManager, FIND_ID_DIALOG_TAG)
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

    companion object {
        const val FIND_ID_DIALOG_TAG = "find_id_custom_dialog"
    }
}
