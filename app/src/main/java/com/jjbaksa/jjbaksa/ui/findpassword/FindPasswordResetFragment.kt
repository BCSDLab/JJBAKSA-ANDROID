package com.jjbaksa.jjbaksa.ui.findpassword

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordResetBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider

class FindPasswordResetFragment : BaseFragment<FragmentFindPasswordResetBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password_reset

    private val viewModel: FindPasswordViewModel by activityViewModels()

    private var isFailNewPassword = false
    private var isFailCheckPassword = false

    override fun initView() {
        val ft = parentFragmentManager.beginTransaction()
        val prev = parentFragmentManager.findFragmentByTag(DIALOG_TAG)
        if (prev != null) ft.remove(prev)
        ft.addToBackStack(null)
    }

    override fun initEvent() {
        backPressed(binding.jjAppBarContainer, requireActivity(), true)
        setNewPassword()
        setCheckPassword()
        completedResult()
        observeDate()
    }

    private fun setNewPassword() {
        binding.inputNewPasswordEditText.also { newPasswordEditText ->
            newPasswordEditText.setOnFocusChangeListener { _, _ -> }
            newPasswordEditText.addTextChangedListener { newPassword ->
                if (isFailNewPassword) {
                    newPasswordEditText.editTextBackground =
                        ContextCompat.getDrawable(requireContext(), R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8)
                    isFailNewPassword = false
                }
                binding.completeButton.also {
                    it.isSelected = isVisibleButton(
                        newPassword = newPassword.toString(),
                        checkPassword = binding.inputCheckPasswordEditText.editTextText
                    )
                    it.isEnabled = isVisibleButton(
                        newPassword = newPassword.toString(),
                        checkPassword = binding.inputCheckPasswordEditText.editTextText
                    )
                }
            }
        }
    }
    private fun setCheckPassword() {
        binding.inputCheckPasswordEditText.also { checkPasswordEditText ->
            checkPasswordEditText.setOnFocusChangeListener { _, _ -> }
            checkPasswordEditText.addTextChangedListener { checkPassword ->
                binding.completeButton.also {
                    if (isFailCheckPassword) {
                        checkPasswordEditText.editTextBackground =
                            ContextCompat.getDrawable(requireContext(), R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8)
                        isFailCheckPassword = false
                    }
                    it.isSelected = isVisibleButton(
                        newPassword = binding.inputNewPasswordEditText.editTextText,
                        checkPassword = checkPassword.toString()
                    )
                    it.isEnabled = isVisibleButton(
                        newPassword = binding.inputNewPasswordEditText.editTextText,
                        checkPassword = checkPassword.toString()
                    )
                }
            }
        }
    }

    private fun isVisibleButton(newPassword: String, checkPassword: String): Boolean =
        newPassword.isNotEmpty() && checkPassword.isNotEmpty()

    private fun completedResult() {
        binding.completeButton.setOnClickListener {
            if (binding.inputNewPasswordEditText.editTextText != binding.inputCheckPasswordEditText.editTextText) {
                failedPassword()
                showSnackBar(getString(R.string.not_correct_password))
            } else {
                viewModel.setNewPassword(binding.inputCheckPasswordEditText.editTextText)
            }
        }
    }
    override fun subscribe() {}

    private fun observeDate() {
        viewModel.newPasswordResult.observe(
            viewLifecycleOwner
        ) {
            if (it.isSuccess) {
                ConfirmDialog(
                    getString(R.string.complete_find_password),
                    getString(R.string.retry_password),
                    getString(R.string.complete),
                    { activity?.finish() }
                ).show(parentFragmentManager, DIALOG_TAG)
            } else {
                failedPassword()
                showSnackBar(getString(R.string.password_rule_not_match))
            }
        }
    }

    private fun failedPassword() {
        isFailNewPassword = true
        isFailCheckPassword = true
        binding.inputNewPasswordEditText.editTextBackground =
            ContextCompat.getDrawable(requireContext(), R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
        binding.inputCheckPasswordEditText.editTextBackground =
            ContextCompat.getDrawable(requireContext(), R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
        KeyboardProvider().hideKeyboard(requireContext(), binding.root)
    }

    companion object {
        const val DIALOG_TAG = "find_password_custom_dialog"
    }
}
