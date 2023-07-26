package com.jjbaksa.jjbaksa.ui.changepassword

import android.graphics.drawable.Drawable
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityChangePasswordBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.ui.changepassword.viewmodel.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_change_password
    private val viewModel: ChangePasswordViewModel by viewModels()

    var currentPasswordText = ""
    var newPasswordText = ""
    var checkPasswordText = ""
    var isFailedCurrentPassword = false
    var isFailedNewPassword = false

    override fun initView() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
        observeData()
    }

    private fun observeData() {
        viewModel.currentPasswordState.observe(this) {
            isFailedCurrentPassword = !it?.isSuccess!!
            if (isFailedCurrentPassword) {
                binding.currentPasswordEditText.editTextBackground = failButtonBackground()
                showSnackBar(it?.msg.toString(), getString(R.string.cancel))
            } else {
                if (newPasswordText != checkPasswordText) {
                    showSnackBar(
                        getString(R.string.not_match_new_password),
                        getString(R.string.cancel)
                    )
                    binding.newPasswordEditText.editTextBackground = failButtonBackground()
                    binding.checkNewPasswordEditText.editTextBackground = failButtonBackground()
                    isFailedNewPassword = true
                } else {
                    viewModel.setNewPassword(binding.newPasswordEditText.editTextText)
                }
            }
        }
        viewModel.newPasswordState.observe(this) {
            if (it?.isSuccess!!) {
                setConfirmDialog()
            } else {
                showSnackBar(it.msg.toString(), getString(R.string.cancel))
                binding.newPasswordEditText.editTextBackground = failButtonBackground()
                binding.checkNewPasswordEditText.editTextBackground = failButtonBackground()
                isFailedNewPassword = true
            }
        }
        viewModel.isEnableButton.observe(this) {
            binding.changePasswordButton.isEnabled = it
            binding.changePasswordButton.isSelected = it
        }
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        setCurrentPassword()
        setNewPassword()
        setCheckNewPassword()
        setConfirmButton()
    }

    private fun setCurrentPassword() {
        binding.currentPasswordEditText.also {
            it.setOnFocusChangeListener { _, _ -> }
            it.addTextChangedListener { currentPassword ->
                if (isFailedCurrentPassword) {
                    it.editTextBackground = comeBackButtonBackground()
                    isFailedCurrentPassword = false
                }
                currentPasswordText = currentPassword.toString()
                setEditTextState()
            }
        }
    }

    private fun setNewPassword() {
        binding.newPasswordEditText.also {
            it.setOnFocusChangeListener { _, _ -> }
            it.addTextChangedListener { newPassword ->
                if (isFailedNewPassword) {
                    it.editTextBackground = comeBackButtonBackground()
                    binding.checkNewPasswordEditText.editTextBackground = comeBackButtonBackground()
                    isFailedNewPassword = false
                }
                newPasswordText = newPassword.toString()
                setEditTextState()
            }
        }
    }

    private fun setCheckNewPassword() {
        binding.checkNewPasswordEditText.also {
            it.setOnFocusChangeListener { _, _ -> }
            it.addTextChangedListener { checkNewPassword ->
                if (isFailedNewPassword) {
                    it.editTextBackground = comeBackButtonBackground()
                    binding.newPasswordEditText.editTextBackground = comeBackButtonBackground()
                    isFailedNewPassword = false
                }
                checkPasswordText = checkNewPassword.toString()
                setEditTextState()
            }
        }
    }

    private fun isNotEmptyEditText(): Boolean =
        currentPasswordText.isNotEmpty() && newPasswordText.isNotEmpty() && checkPasswordText.isNotEmpty()

    private fun setEditTextState() {
        if (isNotEmptyEditText()) {
            viewModel.setEnabledButton(true)
        } else {
            viewModel.setEnabledButton(false)
        }
    }

    private fun setConfirmButton() {
        binding.changePasswordButton.setOnClickListener {
            viewModel.checkPassword(binding.currentPasswordEditText.editTextText)
        }
    }

    private fun failButtonBackground(): Drawable? = ContextCompat.getDrawable(
        this,
        R.drawable.shape_rectf6bf54_solid_radius_100_stroke_ff7f23
    )

    private fun comeBackButtonBackground(): Drawable? = ContextCompat.getDrawable(
        this,
        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
    )

    private fun setConfirmDialog() {
        ConfirmDialog(
            getString(R.string.success_change_password),
            getString(R.string.retry_password),
            getString(R.string.complete),
            { finish() }
        ).show(supportFragmentManager, CHANGE_PASSWORD_CONFIRM_DIALOG)
    }

    companion object {
        const val CHANGE_PASSWORD_CONFIRM_DIALOG = "CHANGE_PASSWORD_CONFIRM_DIALOG"
    }
}
