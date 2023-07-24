package com.jjbaksa.jjbaksa.ui.login

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
    private var isVisiblePasswordIcon = true
    override val layoutId: Int
        get() = R.layout.activity_login
    val viewModel: LoginViewModel by viewModels()
    override fun initView() {
        binding.vm = viewModel
//        viewModel.getAutoLoginFlag()
    }

    override fun subscribe() {
        with(viewModel) {

            loginState.observe(this@LoginActivity) {
                if (it != null) {
                    if (it.isSuccess) {
                        goToMainActivity()
                    } else {
                        if (it.erroMessage.isNotEmpty()) {
                            showSnackBar(it.erroMessage, getString(R.string.cancel))
                            setEditTextErrorUI()
                        }
                    }
                }
            }
        }
    }

    private fun setEditTextErrorUI() {
        with(binding) {
            editTextId.background =
                getDrawable(R.drawable.shape_rectf6bf54_solid_radius_100_stroke_ff7f23)
            editTextPassword.background =
                getDrawable(R.drawable.shape_rectf6bf54_solid_radius_100_stroke_ff7f23)
        }
    }

    override fun initEvent() {
        setVisiblePassword()
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
                viewModel.login(binding.switchAutoLogin.isChecked)
            }
            editTextId.addTextChangedListener {
                editTextId.background = getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_100)
                buttonLogin.isSelected =
                    it.toString().isNotEmpty() && editTextPassword.text.toString().isNotEmpty()
            }
            editTextPassword.addTextChangedListener {
                editTextPassword.background =
                    getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_100)
                buttonLogin.isSelected =
                    it.toString().isNotEmpty() && editTextId.text.toString().isNotEmpty()
            }
        }
    }

    private fun setVisiblePassword() {
        binding.imageViewVisiblePassword.setImageDrawable(getDrawable(R.drawable.sel_jj_edit_text_password_show_unchecked))
        binding.imageViewVisiblePassword.apply {
            setOnClickListener {
                isVisiblePasswordIcon = !isVisiblePasswordIcon
                if (isVisiblePasswordIcon) {
                    setImageDrawable(getDrawable(R.drawable.sel_jj_edit_text_password_show_unchecked))
                    binding.editTextPassword.apply {
                        transformationMethod = PasswordTransformationMethod.getInstance()
                        setSelection(this.length())
                    }
                } else {
                    setImageDrawable(getDrawable(R.drawable.sel_jj_edit_text_password_show_checked))
                    binding.editTextPassword.apply {
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        setSelection(this.length())
                    }
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
