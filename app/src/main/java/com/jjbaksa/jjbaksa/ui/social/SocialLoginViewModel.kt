package com.jjbaksa.jjbaksa.ui.social

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.jjbaksa.BuildConfig
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.RegexUtil
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialLoginViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel() {

    private val _loginState = SingleLiveEvent<LoginResult>()
    val loginState: SingleLiveEvent<LoginResult> get() = _loginState

    val account = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val isAutoLogin = MutableLiveData<Boolean>(false)

    private var kakaoEmail: String = ""
    private var kakaoAccount: String = ""
    private var kakaoNickname: String = ""

    private val kakaoSignUpId = "kakao"
    private val googleSignUpId = "google"
    private val naverSignUpId = "naver"

    private var naverAccount: String = ""
    private var naverEmail: String = ""
    private var naverNickname: String = ""
    private var naverAccessToken: String = ""

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
        } else if (token != null) {
            UserApiClient.instance.me { user, meError ->
                if (error != null) {
                } else if (user != null) {
                    kakaoEmail = user.kakaoAccount?.email.toString()
                    kakaoAccount = user.id.toString()
                    kakaoNickname = user.kakaoAccount?.profile?.nickname.toString()
                    val newKakaoId = kakaoSignUpId + kakaoAccount
                    socialLogin(newKakaoId, 1)
                }
            }
        }
    }

    val oAuthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            naverAccessToken = NaverIdLoginSDK.getAccessToken().toString()
            checkNaverSocialLogin() {
                socialLogin(naverAccount, 2)
            }
        }

        override fun onError(errorCode: Int, message: String) {
        }

        override fun onFailure(httpStatus: Int, message: String) {
        }
    }

    fun socialLogin(account: String, socialNum: Int) {
        viewModelScope.launch(ceh) {
            repository.postLogin(account, BuildConfig.social_login_password, isAutoLogin.value!!) {
                _loginState.value = it
                when (it.erroMessage) {
                    "3" -> {
                        when (socialNum) {
                            1 -> {
                                socialSignUp(
                                    account,
                                    getCustomKakaoSignUpEmail(),
                                    getCustomKakaoId(),
                                    socialNum
                                )
                            }
                            2 -> {
                                socialSignUp(
                                    getCustomNaverId(),
                                    naverEmail,
                                    naverNickname,
                                    socialNum
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun socialSignUp(id: String, email: String, nickname: String, socialNum: Int) {
        val signUpReq = SignUpReq(id, email, nickname, BuildConfig.social_login_password)
        viewModelScope.launch {
            runCatching {
                repository.postSignUp(signUpReq)
            }.onSuccess {
                socialLogin(id, socialNum)
            }.onFailure {
            }
        }
    }

    fun getCustomKakaoSignUpEmail(): String {
        val kakaoCustomEmail = getCustomKakaoId() + "@kakao.com"
        return kakaoCustomEmail
    }

    fun checkNaverSocialLogin(onSuccess: () -> Unit) {
        NidOAuthLogin().callProfileApi(object :
                NidProfileCallback<NidProfileResponse> {
                override fun onSuccess(result: NidProfileResponse) {
                    naverAccount = result.profile?.id.toString()
                    naverEmail = result.profile?.email.toString()
                    naverNickname = result.profile?.nickname.toString()
                    naverAccount = RegexUtil.matchNaverAccount(naverAccount)
                    onSuccess()
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            })
    }

    fun getCustomKakaoId(): String {
        return kakaoSignUpId + kakaoAccount
    }

    fun getCustomGoogleId(account: String): String {
        return googleSignUpId + account
    }

    fun getCustomNaverId(): String {
        return naverSignUpId + naverAccount.substring(0 until 8)
    }

    fun getAutoLoginFlag() {
        viewModelScope.launch(ceh) {
            if (repository.getAutoLoginFlag()) {
                isAutoLogin.value = true
                account.value = repository.getAccount()
                password.value = repository.getPasswrod()
            }
        }
    }

    companion object {
        const val TAG = "SocialLogin"
    }
}
