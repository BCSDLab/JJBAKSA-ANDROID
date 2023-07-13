package com.jjbaksa.jjbaksa.ui.changepassword

import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityChangePasswordBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_change_password

    override fun initView() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        setCurrentPassword()
        setNewPassword()
        setCheckNewPassword()
    }

    private fun setCurrentPassword() {
        binding.currentPasswordEditText.also {
            it.setOnFocusChangeListener { _, _ -> }
            it.addTextChangedListener { currentPassword -> }
        }
    }

    private fun setNewPassword() {
        binding.newPasswordEditText.also {
            it.setOnFocusChangeListener { _, _ -> }
            it.addTextChangedListener { newPassword -> }
        }
    }

    private fun setCheckNewPassword() {
        binding.checkNewPasswordEditText.also {
            it.setOnFocusChangeListener { _, _ -> }
            it.addTextChangedListener { checkNewPassword -> }
        }
    }

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
