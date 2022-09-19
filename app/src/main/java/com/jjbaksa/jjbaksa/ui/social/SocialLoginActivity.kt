package com.jjbaksa.jjbaksa.ui.social

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySocialLoginBinding

class SocialLoginActivity : BaseActivity<ActivitySocialLoginBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_social_login

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private var userEmail: String = ""
    private var tokenId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        activityResultLauncher = registerForActivityResult(

            // Google Auth 로그인 결과 수신
            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback
            { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                    task.getResult(ApiException::class.java)?.let { account ->
                        tokenId = account.idToken
                       // Log.d("userToken", tokenId.toString())
                        //최초접근, 회원가입 구현을 해야하는가? x
                        if (tokenId != null && tokenId != "") {
                           // Log.d("userToken", tokenId.toString())
                            val authCredential: AuthCredential =
                                GoogleAuthProvider.getCredential(account.idToken, null)
                         
                            firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener {
                                    if (firebaseAuth.currentUser != null) {
                                        val firebaseUser: FirebaseUser = firebaseAuth.currentUser!!
                                        userEmail = firebaseUser.email.toString()
                                        val googleSignInToken = account.idToken ?: ""
                                        Log.d("userToken", tokenId.toString())
                                    }
                                }
                        }
                    }
                }
            })

    }

    override fun initView() {
        binding.run {
            btnSocialLoginGoogleSignIn.setOnClickListener {
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

    override fun subscribe() {
    }

    override fun initEvent() {

    }

}
