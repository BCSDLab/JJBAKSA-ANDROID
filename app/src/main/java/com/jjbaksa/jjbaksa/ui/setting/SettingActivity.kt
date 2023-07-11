package com.jjbaksa.jjbaksa.ui.setting

import androidx.activity.viewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySettingBinding
import com.jjbaksa.jjbaksa.ui.setting.viewmodel.SettingViewModel
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

    fun goToChangePassword() { }
    fun goToPrivacyPolicy() { }
    fun goToNotice() { }
    fun goToInquiry() { }
    fun logout() { }
    fun withdraw() {}
}
