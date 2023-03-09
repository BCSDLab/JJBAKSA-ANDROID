package com.jjbaksa.jjbaksa.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityLoginBinding
import com.jjbaksa.jjbaksa.ui.findid.FindIdActivity
import com.jjbaksa.jjbaksa.ui.findpassword.FindPasswordActivity
import com.jjbaksa.jjbaksa.ui.mainpage.MainPageActivity
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
        viewModel.getAutoLoginFlag()
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
            textViewFindId.setOnClickListener {
                goToFindIdActivity()
            }
            textViewFindPassword.setOnClickListener {
                goToFindPasswordActivity()
            }
            buttonLogin.setOnClickListener {
                viewModel.login()
            }
            editTextId.addTextChangedListener {
                buttonLogin.isSelected =
                    it.toString().isNotEmpty() && editTextPassword.text.toString().isNotEmpty()
            }
            editTextPassword.addTextChangedListener {
                buttonLogin.isSelected = it.toString().isNotEmpty() && editTextId.text.toString().isNotEmpty()
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
    fun goToFindPasswordActivity() {
        Intent(this, FindPasswordActivity::class.java).also { startActivity(it) }
    }
    fun goToMainActivity() {
        Intent(this, MainPageActivity::class.java).also { startActivity(it) }
    }
}
