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
import androidx.lifecycle.lifecycleScope
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
import com.jjbaksa.jjbaksa.util.RegexUtil
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthBehavior
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SocialLoginActivity : BaseActivity<ActivitySocialLoginBinding>() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private var kakaoEmail: String = ""
    private var kakaoAccount: String = ""
    private var kakaoNickname: String = ""

    private var naverAccount: String = ""
    private var naverEmail: String = ""
    private var naverNickname: String = ""
    private var naverAccessToken: String = ""

    private var googleUserEmail: String = ""
    private var googleAccount: String = ""
    private var googleTokenId: String? = ""
    val viewModel: SocialLoginViewModel by viewModels()

    companion object {
        const val TAG = "SocialLogin"
    }

    override val layoutId: Int
        get() = R.layout.activity_social_login

    override fun initView() {
        initGoogleLogin()
        initNaverLogin()
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            buttonKakaoLogin.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val oAuthToken = UserApiClient.loginWithKakao(this@SocialLoginActivity)
                    Log.d(TAG, "kakao token > ${oAuthToken.accessToken}")
                    checkKakaoSocialLogin(){
                        viewModel.isKakaoSocialIdExist(kakaoAccount)
                    }
                }
//                viewModel.onKakaoButtonClick()

                viewModel.isKakaoSignUpSuccess.observe(this@SocialLoginActivity) {
                    Log.i(TAG, "iskakaosignupsuccess 참 됨")
                    viewModel.login(viewModel.getCustomKakaoId(kakaoAccount))
                }
            }
            buttonNaverLogin.setOnClickListener {
                val oAuthLoginCallback = object : OAuthLoginCallback {
                    override fun onSuccess() {
                        naverAccessToken = NaverIdLoginSDK.getAccessToken().toString()
                        Log.e(TAG, "naverAccessToken : ${NaverIdLoginSDK.getAccessToken()}")
                        NidOAuthLogin().callProfileApi(object :
                            NidProfileCallback<NidProfileResponse> {
                            override fun onSuccess(result: NidProfileResponse) {
                                naverAccount = result.profile?.id.toString()
                                naverEmail = result.profile?.email.toString()
                                naverNickname = result.profile?.nickname.toString()
                                Log.e(TAG, "네이버 로그인한 유저 정보 - 계정 : $naverAccount")
                                Log.e(TAG, "네이버 로그인한 유저 정보 - 이메일 : $naverEmail")
                                Log.e(TAG, "네이버 로그인한 유저 정보 - 닉네임 : $naverNickname")
                                naverAccount = RegexUtil.matchNaverAccount(naverAccount)
                                Log.e(TAG,"정규표현식 적용 네이버 계정 : $naverAccount")
                                viewModel.isNaverSocialIdExist(naverAccount,naverEmail,naverNickname)
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
                        })
                    }

                    override fun onError(errorCode: Int, message: String) {
                        Log.e(TAG, "onError in naver login")
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        //
                    }
                }
                NaverIdLoginSDK.authenticate(this@SocialLoginActivity, oAuthLoginCallback)
                viewModel.isNaverSignUpSuccess.observe(this@SocialLoginActivity) {
                    Log.i(TAG, "isNaversignupsuccess 참 됨")
                    viewModel.login(viewModel.getCustomNaverId(naverAccount))
                }
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

//                viewModel.onGoogleButtonClick()

                viewModel.isGoogleSignUpSuccess.observe(this@SocialLoginActivity) {
                    Log.d(TAG, "isSignupSuccess 참")
                    viewModel.login(googleAccount)
                }
            }
        }
        viewModel.loginState.observe(this@SocialLoginActivity) {
            if (it != null) {
                if (it.isSuccess) {
                    Log.i(TAG, "소셜 로그인 성공?!")
                    goToMainActivity()

                } else {
                    if (it.erroMessage.isNotEmpty()) {
                        showToast(it.erroMessage)
                        Log.i(TAG, "loginstate observe 함수 내부 : ${it.erroMessage}")
                    }
                }
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
                            showToast(googleTokenId.toString())
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
                    Log.d(TAG, "signInWithCredential success!!")
                    val firebaseUser: FirebaseUser = firebaseAuth.currentUser!!
                    googleUserEmail = firebaseUser.email.toString()
                    showToast(firebaseUser.providerId)
                    Log.d(TAG, "providerId : ${firebaseUser.providerId}")
                    Log.d(TAG, googleTokenId.toString())
                    lifecycleScope.launch {
                        isGoogleLoginExist()
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", it.exception)
                }
            }
    }

    private suspend fun isGoogleLoginExist() {
        withContext(Dispatchers.IO) {
            if (viewModel.checkSocialIdExist(viewModel.getCustomGoogleId(googleAccount))) {
                runOnUiThread { showToast("아이디가 존재해요") }
                Log.i(TAG, "아이디 존재")
                viewModel.login(viewModel.getCustomGoogleId(googleAccount))
            } else {
                // token 으로 회원가입에 필요한 정보 요청 후 회원가입
                runOnUiThread { showToast("회원가입 요청할래") }
                Log.i(TAG, "회원가입 요청")
                viewModel.socialKakaoSignUp(viewModel.getCustomGoogleId(googleAccount),
                    "example@gmail.com",
                    "nickname")
            }
        }
    }

    private fun getNaverToken() {

        NaverIdLoginSDK.behavior = NidOAuthBehavior.DEFAULT
        NaverIdLoginSDK.authenticate(this@SocialLoginActivity, object : OAuthLoginCallback {
            override fun onSuccess() {
                Log.d(TAG, "authenticate onSuccess()")
                naverAccessToken = NaverIdLoginSDK.getAccessToken().toString()
                Log.d(TAG, "naver token : ${naverAccessToken}")
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
        })

    }

    private fun goToMainActivity() {
        Intent(this, MainPageActivity::class.java).also { startActivity(it) }
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkKakaoSocialLogin(onSuccess:()->Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")
                kakaoEmail = user.kakaoAccount?.email.toString()
                kakaoAccount = user.id.toString()
                kakaoNickname = user.kakaoAccount?.profile?.nickname.toString()
                onSuccess()
            }
        }
    }

    private fun checkNaverSoicalLogin(onSuccess:()->Unit) {

        Log.d(TAG, "checkNaverSocialLogin 함수 실행")
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                Log.d(TAG, "naver id : ${response.profile?.id}")
                Log.d(TAG, "naver Email : ${response.profile?.email}")
                Log.d(TAG, "naver nickname : ${response.profile?.nickname}")
                naverAccount = response.profile?.id.toString()
                naverEmail = response.profile?.email.toString()
                naverNickname = response.profile?.nickname.toString()
                onSuccess()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.d(TAG, "callProfileApi onFailure()")
                Log.d(TAG, "errorCode : $errorCode / errorDesc : $errorDescription")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })

    }


    private fun startNaverDeleteToken() {
        NidOAuthLogin().callDeleteTokenApi(this, object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
                Toast.makeText(this@SocialLoginActivity, "네이버 아이디 토큰삭제 성공!", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }

            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }
}
