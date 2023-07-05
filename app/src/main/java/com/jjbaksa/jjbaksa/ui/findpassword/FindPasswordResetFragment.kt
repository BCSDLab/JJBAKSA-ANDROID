package com.jjbaksa.jjbaksa.ui.findpassword

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordResetBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel

class FindPasswordResetFragment : BaseFragment<FragmentFindPasswordResetBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password_reset

    private val viewModel: FindPasswordViewModel by activityViewModels()

    private val findPasswordCustomDialog by lazy {
        FindPasswordCustomDialog()
    }

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
            viewModel.setNewPassword(binding.inputCheckPasswordEditText.editTextText)
        }
    }
    override fun subscribe() {}

    private fun observeDate() {
        viewModel.newPasswordResult.observe(
            viewLifecycleOwner
        ) {
            Log.d("로그", "새로운 비번 결과 : $it")
        }
    }

    companion object {
        const val DIALOG_TAG = "find_password_custom_dialog"
    }
}
