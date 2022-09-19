package com.jjbaksa.jjbaksa.ui.social

import androidx.activity.viewModels
import com.jjbaksa.jjbaksa.viewmodel.KakaoLoginViewModel
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySocialLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialLoginActivity : BaseActivity<ActivitySocialLoginBinding>() {

    private val kakaoLoginViewModel: KakaoLoginViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.activity_social_login

    override fun initView() {
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            buttonKakaoLogin.setOnClickListener {
                kakaoLoginViewModel.handleKakaoLogin()
            }
        }
    }
}
