package com.jjbaksa.jjbaksa.ui.social

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jjbaksa.jjbaksa.BuildConfig
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySocialLoginBinding
import com.jjbaksa.jjbaksa.util.UiState
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthBehavior
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SocialLoginActivity : BaseActivity<ActivitySocialLoginBinding>() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private var userEmail: String = ""
    private var tokenId: String? = null

    private val socialLoginViewModel: SocialLoginViewModel by viewModels()

    @Inject
    lateinit var kakaoService: KakaoService

    companion object {
        const val TAG = "SocialLogin"
    }

    override val layoutId: Int
        get() = R.layout.activity_social_login

    override fun initView() {
        initNaverLogin()
        initGoogleLogin()
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }

        with(binding) {
            buttonKakaoLogin.setOnClickListener {
                kakaoService.initKakaoLogin {
                    Timber.tag("kakao_token").e("$it")
//                    socialLoginViewModel.postLoginSNS("Bearer $it", KAKAO)
                }
            }
            buttonNaverLogin.setOnClickListener {
                NaverIdLoginSDK.behavior = NidOAuthBehavior.DEFAULT
                NaverIdLoginSDK.authenticate(
                    this@SocialLoginActivity,
                    object : OAuthLoginCallback {
                        override fun onSuccess() {
                            Log.d(TAG, "onSuccess")
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                            Log.d(TAG, "authenticate onFailure()")
                            Log.d(TAG, "errorCode : $errorCode / errorDesc : $errorDescription")
                        }

                        override fun onError(errorCode: Int, message: String) {
                            onFailure(errorCode, message)
                        }
                    }
                )
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

        observeData()
    }

    private fun observeData() {
        socialLoginViewModel.kakaoLoginState.flowWithLifecycle(lifecycle).onEach { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    Timber.tag("kakao").e("success")
                }

                is UiState.Failure -> {
                    Timber.tag("kakao").e("failure")
                }

                else -> Unit // Loading etc...
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
                        tokenId = account.idToken
                        Log.d(TAG, tokenId.toString())
                        if (tokenId != null && tokenId != "") {
                            Log.d(TAG, tokenId.toString())
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
            .addOnCompleteListener {
                if (firebaseAuth.currentUser != null) {
                    val firebaseUser: FirebaseUser = firebaseAuth.currentUser!!
                    userEmail = firebaseUser.email.toString()
                    Log.d(TAG, tokenId.toString())
                } else {
                    Log.d(TAG, "signInWithCredential failure")
                }
            }
    }
}
