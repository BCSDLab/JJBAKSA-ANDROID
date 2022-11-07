package com.jjbaksa.jjbaksa.ui.login

import android.content.Intent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityLoginBinding
import com.jjbaksa.jjbaksa.ui.findid.FindIdActivity
import com.jjbaksa.jjbaksa.ui.signup.SignUpActivity
import com.jjbaksa.jjbaksa.ui.social.SocialLoginActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initView() {
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            buttonSocialLogin.setOnClickListener {
                goToSocialLoginActivity()
            }
            textViewSignUp.setOnClickListener {
                goToSignUpActivity()
            }
            textViewFindId.setOnClickListener {
                goToFindIdActivity()
            }
        }
    }
    fun goToSocialLoginActivity() {
        Intent(this, SocialLoginActivity::class.java).also { startActivity(it) }
    }
    fun goToSignUpActivity() {
        Intent(this, SignUpActivity::class.java).also { startActivity(it) }
    }
    fun goToFindIdActivity() {
        Intent(this, FindIdActivity::class.java).also { startActivity(it) }
    }
}
