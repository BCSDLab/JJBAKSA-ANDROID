package com.jjbaksa.jjbaksa.ui.setting

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySettingBinding
import com.jjbaksa.jjbaksa.dialog.SignOutDialog
import com.jjbaksa.jjbaksa.ui.changepassword.ChangePasswordActivity
import com.jjbaksa.jjbaksa.ui.login.LoginActivity
import com.jjbaksa.jjbaksa.ui.setting.viewmodel.SettingViewModel
import com.jjbaksa.jjbaksa.ui.withdrawal.WithdrawalActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_setting

    private val viewModel: SettingViewModel by viewModels()

    override fun initView() {
        binding.also {
            it.lifecycleOwner = this
            it.view = this
        }
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
    }

    fun goToChangePassword() {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }

    fun goToPrivacyPolicy() {}
    fun goToNotice() {}
    fun goToInquiry() {}
    fun logout() {
        SignOutDialog(
            title = getString(R.string.sign_out_title_text),
            msg = getString(R.string.sign_out_content_text),
            confirmClick = {
                viewModel.signOut()
                ActivityCompat.finishAffinity(this)
                startActivity(Intent(this, LoginActivity::class.java))
            }
        ).show(supportFragmentManager, SIGN_OUT_DIALOG_TAG)
    }

    fun withdraw() {
        startActivity(Intent(this, WithdrawalActivity::class.java))
    }

    companion object {
        const val SIGN_OUT_DIALOG_TAG = "SIGN_OUT_DIALOG_TAG"
    }
}
