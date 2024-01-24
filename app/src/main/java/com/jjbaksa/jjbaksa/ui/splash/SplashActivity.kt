package com.jjbaksa.jjbaksa.ui.splash

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySplashBinding
import com.jjbaksa.jjbaksa.ui.login.LoginActivity
import com.jjbaksa.jjbaksa.ui.mainpage.MainPageActivity
import com.jjbaksa.jjbaksa.ui.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_splash

    private val viewModel: SplashViewModel by viewModels()

    private val loginResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // LoginActivity Result Handle
        }
    private val mainResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // MainPageActivity Result Handle
        }

    override fun initView() {
        viewModel.getAutoLogin()
    }

    override fun initEvent() {
    }

    override fun subscribe() {
        viewModel.autoLogin.observe(this) { isLogin ->
            if (isLogin) {
                viewModel.getUserMe()
            } else {
                goToLoginActivity()
                finish()
            }
        }
        viewModel.authLoginState.observe(this) {
            try {
                if (it) goToMainActivity() else goToLoginActivity()
            } catch (e: Exception) {
                goToLoginActivity()
            } finally {
                finish()
            }
        }
    }

    private fun goToLoginActivity() {
        loginResult.launch(Intent(this, LoginActivity::class.java))
    }

    private fun goToMainActivity() {
        mainResult.launch(Intent(this, MainPageActivity::class.java))
    }
}
