package com.jjbaksa.jjbaksa.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private val viewModel:SplashViewModel by viewModels()

    private val loginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        // LoginActivity Result Handle
    }
    private val mainResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        // MainPageActivity Result Handle
    }

    override fun initView() {
        observeData()
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        viewModel.getAutoLogin()
    }

    private fun observeData() {
        viewModel.autoLogin.observe(this) { isLogin ->
            if (isLogin) {
                viewModel.getAccessToken()
            } else {
                loginResult.launch(Intent(this, LoginActivity::class.java))
            }
        }
        viewModel.authLoginState.observe(this) {
            if (it) {
                mainResult.launch(Intent(this, MainPageActivity::class.java))
                finish()
            } else {

            }
        }
    }
}