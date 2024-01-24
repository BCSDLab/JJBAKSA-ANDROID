package com.jjbaksa.jjbaksa.ui.login

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityLoginBinding
import com.jjbaksa.jjbaksa.ui.findid.FindIdActivity
import com.jjbaksa.jjbaksa.ui.findpassword.FindPasswordActivity
import com.jjbaksa.jjbaksa.ui.login.viewmodel.LoginViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.MainPageActivity
import com.jjbaksa.jjbaksa.ui.signup.SignUpActivity
import com.jjbaksa.jjbaksa.ui.social.SocialLoginActivity
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import com.jjbaksa.jjbaksa.util.controlSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private var isVisiblePasswordIcon = true
    override val layoutId: Int
        get() = R.layout.activity_login
    val viewModel: LoginViewModel by viewModels()
    override fun initView() {
        binding.vm = viewModel
    }

    override fun subscribe() {
        with(viewModel) {
            loginResult.observe(this@LoginActivity) {
                if (it.isSuccess) {
                    goToActivity(MainPageActivity::class.java)
                    finish()
                } else {
                    showSnackBar(it.errorMessage, getString(R.string.cancel))
                    setEditTextErrorUI()
                    KeyboardProvider(this@LoginActivity).hideKeyboard(binding.editTextId)
                }
            }
        }
    }

    private fun setEditTextErrorUI() {
        controlSoftKeyboard(binding.root, false)
        showSnackBar(getString(R.string.non_exist_user), getString(R.string.do_register)) {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        with(binding) {
            editTextId.background =
                ContextCompat.getDrawable(
                    this@LoginActivity,
                    R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23
                )
            editTextPassword.background =
                ContextCompat.getDrawable(
                    this@LoginActivity,
                    R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23
                )
        }
    }

    override fun initEvent() {
        setVisiblePassword()
        with(binding) {
            buttonSocialLogin.setOnClickListener {
                goToActivity(SocialLoginActivity::class.java)
            }
            textViewSignUp.setOnClickListener {
                goToActivity(SignUpActivity::class.java)
            }
            textViewFindId.setOnClickListener {
                goToActivity(FindIdActivity::class.java)
            }
            textViewFindPassword.setOnClickListener {
                goToActivity(FindPasswordActivity::class.java)
            }
            buttonLogin.setOnClickListener {
                viewModel.login(binding.switchAutoLogin.isChecked)
            }
            editTextId.addTextChangedListener {
                editTextId.background = ContextCompat.getDrawable(
                    this@LoginActivity,
                    R.drawable.shape_rect_eeeeee_solid_radius_100
                )
                buttonLogin.isSelected =
                    it.toString().isNotEmpty() && editTextPassword.text.toString().isNotEmpty()
            }
            editTextPassword.addTextChangedListener {
                editTextPassword.background = ContextCompat.getDrawable(
                    this@LoginActivity,
                    R.drawable.shape_rect_eeeeee_solid_radius_100
                )
                buttonLogin.isSelected =
                    it.toString().isNotEmpty() && editTextId.text.toString().isNotEmpty()
            }
        }
    }

    private fun setVisiblePassword() {
        binding.imageViewVisiblePassword.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.sel_jj_edit_text_password_show_unchecked)
        )
        binding.imageViewVisiblePassword.apply {
            setOnClickListener {
                isVisiblePasswordIcon = !isVisiblePasswordIcon
                if (isVisiblePasswordIcon) {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            this@LoginActivity,
                            R.drawable.sel_jj_edit_text_password_show_unchecked
                        )
                    )
                    binding.editTextPassword.apply {
                        transformationMethod = PasswordTransformationMethod.getInstance()
                        setSelection(this.length())
                    }
                } else {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            this@LoginActivity,
                            R.drawable.sel_jj_edit_text_password_show_checked
                        )
                    )
                    binding.editTextPassword.apply {
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        setSelection(this.length())
                    }
                }
            }
        }
    }

    private fun goToActivity(classActivity: Class<*>) {
        Intent(this, classActivity).also { startActivity(it) }
    }
}
