package com.jjbaksa.jjbaksa.ui.social

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.jjbaksa.jjbaksa.BuildConfig
import androidx.activity.result.ActivityResultCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySocialLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthBehavior
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialLoginActivity : BaseActivity<ActivitySocialLoginBinding>() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private var userEmail: String = ""
    private var tokenId: String? = null

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
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Log.d(TAG, "토큰 정보 보기 실패", error)
                    } else if (tokenInfo != null) {
                        Log.d(TAG, "토큰 정보 보기 성공", error)
                    }
                }

                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오계정으로 로그인 실패", error)
                    } else if (token != null) {
                        Log.e(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    }
                }

                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SocialLoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@SocialLoginActivity) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "카카오톡으로 로그인 실패", error)
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            }
                            UserApiClient.instance.loginWithKakaoAccount(
                                this@SocialLoginActivity,
                                callback = callback
                            )
                        } else if (token != null) {
                            Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(
                        this@SocialLoginActivity,
                        callback = callback
                    )
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
