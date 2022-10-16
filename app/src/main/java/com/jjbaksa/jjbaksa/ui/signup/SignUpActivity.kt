package com.jjbaksa.jjbaksa.ui.signup

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySignUpBinding
import com.jjbaksa.jjbaksa.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.activity_sign_up

    override fun initView() {
        binding.lifecycleOwner = this
    }

    override fun subscribe() {
    }

    override fun initEvent() {
    }
}
