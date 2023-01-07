package com.jjbaksa.jjbaksa.ui.social

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.jjbaksa.jjbaksa.BuildConfig
import androidx.activity.result.ActivityResultCallback
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySocialLoginBinding
import com.jjbaksa.jjbaksa.ui.mainpage.MainPageActivity
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialLoginActivity : BaseActivity<ActivitySocialLoginBinding>() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private var googleUserEmail: String = ""
    private var googleAccount: String = ""
    private var googleTokenId: String? = ""
    val viewModel: SocialLoginViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.activity_social_login

    override fun initView() {
        initGoogleLogin()
        initNaverLogin()
        viewModel.getAutoLoginFlag()
    }

    override fun subscribe() {
        viewModel.loginState.observe(this@SocialLoginActivity) {
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

    override fun initEvent() {
        with(binding) {
            buttonKakaoLogin.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SocialLoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@SocialLoginActivity, callback = viewModel.kakaoLoginCallback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@SocialLoginActivity, callback = viewModel.kakaoLoginCallback)
                }
            }
            buttonNaverLogin.setOnClickListener {
                NaverIdLoginSDK.authenticate(this@SocialLoginActivity, viewModel.oAuthLoginCallback)
            }

            buttonGoogleLogin.setOnClickListener {
                val googleSignInOptions =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()

                val googleSignInClient =
                    GoogleSignIn.getClient(this@SocialLoginActivity, googleSignInOptions)

                val signInIntent: Intent = googleSignInClient.signInIntent
                activityResultLauncher.launch(signInIntent)
            }
        }
    }

    private fun initNaverLogin() {
        NaverIdLoginSDK.apply {
            showDevelopersLog(true)
            initialize(
                this@SocialLoginActivity,
                BuildConfig.naver_client_id,
                BuildConfig.naver_client_secret,
                BuildConfig.naver_client_name
            )
            isShowMarketLink = true
            isShowBottomTab = true
        }
    }

    private fun initGoogleLogin() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback
            { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                    task.getResult(ApiException::class.java)?.let { account ->
                        googleTokenId = account.idToken
                        if (googleTokenId != null && googleTokenId != "") {
                            Log.d(TAG, googleTokenId.toString())
                            val authCredential: AuthCredential =
                                GoogleAuthProvider.getCredential(account.idToken, null)
                            firebaseAuthWithGoogle(authCredential, account)
                        }
                    }
                }
            }
        )
    }

    private fun firebaseAuthWithGoogle(
        authCredential: AuthCredential,
        account: GoogleSignInAccount,
    ) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val firebaseUser: FirebaseUser = firebaseAuth.currentUser!!
                    googleUserEmail = firebaseUser.email.toString()
                    showToast(firebaseUser.providerId)
                    Log.d(TAG, "providerId : ${firebaseUser.providerId}")
                    Log.d(TAG, googleTokenId.toString())
                } else {
                    showToast("signInWithCredential:failure")
                }
            }
    }

    private fun goToMainActivity() {
        Intent(this, MainPageActivity::class.java).also { startActivity(it) }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "SocialLogin"
    }
}
