package com.jjbaksa.jjbaksa.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityLoginBinding
import com.jjbaksa.jjbaksa.ui.main.MainActivity
import com.jjbaksa.jjbaksa.ui.signup.SignUpActivity
import com.jjbaksa.jjbaksa.ui.social.SocialLoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_login
    val viewModel: LoginViewModel by viewModels()
    override fun initView() {
        binding.vm = viewModel
    }

    override fun subscribe() {
        with(viewModel) {
            loginState.observe(this@LoginActivity) {
                if (it != null) {
                    if (it.isSuccess) {
                        goToMainActivity()
                    } else {
                        if (it.erroMessage.isNotEmpty()) {
                            showToast(it.erroMessage)
                        }
                    }
                }
            }
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun initEvent() {
        with(binding) {
            buttonSocialLogin.setOnClickListener {
                goToSocialLoginActivity()
            }
            textViewSignUp.setOnClickListener {
                goToSignUpActivity()
            }
            buttonLogin.setOnClickListener {
                if (viewModel.account.value!!.isNotEmpty() && viewModel.password.value!!.isNotEmpty()) {
                    viewModel.login()
                }
            }
        }
    }

    fun goToSocialLoginActivity() {
        Intent(this, SocialLoginActivity::class.java).also { startActivity(it) }
    }

    fun goToSignUpActivity() {
        Intent(this, SignUpActivity::class.java).also { startActivity(it) }
    }

    fun goToMainActivity() {
        Intent(this, MainActivity::class.java).also { startActivity(it) }
    }
}
