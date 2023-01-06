package com.jjbaksa.jjbaksa.ui.social

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.jjbaksa.BuildConfig
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialLoginViewModel @Inject constructor(
    private val repository: UserRepository,
) : BaseViewModel() {

    private val _loginState = SingleLiveEvent<LoginResult>()
    val loginState: SingleLiveEvent<LoginResult> get() = _loginState

    private val _isNaverSignUpSuccess = SingleLiveEvent<Boolean>()
    val isNaverSignUpSuccess: LiveData<Boolean> = _isNaverSignUpSuccess

    private val _isKakaoSignUpSuccess = SingleLiveEvent<Boolean>()
    val isKakaoSignUpSuccess: LiveData<Boolean> = _isKakaoSignUpSuccess

    private val _isGoogleSignUpSuccess = SingleLiveEvent<Boolean>()
    val isGoogleSignUpSuccess: LiveData<Boolean> = _isGoogleSignUpSuccess

    val account = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val isAutoLogin = MutableLiveData<Boolean>(false)

    private var kakaoEmail: String = ""
    private var kakaoAccount: String = ""
    private var kakaoNickname: String = ""

    val kakaoSignUpId = "kakao"
    val googleSignUpId = "google"
    val naverSignUpId = "naver"

    suspend fun checkSocialIdExist(account: String): RespResult<Boolean> {
        return repository.checkAccountAvailable(account)
    }

    fun socialLogin(account: String) {
        viewModelScope.launch(ceh) {
            repository.postLogin(account, BuildConfig.social_login_password, isAutoLogin.value!!) {
                _loginState.value = it
            }
        }
    }

    fun kakaoSignUp(id: String, email: String, nickname: String) {
        val signUpReq = SignUpReq(id, email, nickname, BuildConfig.social_login_password)
        viewModelScope.launch {
            runCatching {
                repository.postSignUp(signUpReq)
            }.onSuccess {
                if (it?.account != null) {
                    _isKakaoSignUpSuccess.value = true
                } else socialLogin(id)
            }.onFailure {
            }
        }
    }

    fun isKakaoSocialIdExist() {
        viewModelScope.launch {
            if (checkSocialIdExist(getCustomKakaoId()) == RespResult.Success(true)) {
                socialLogin(getCustomKakaoId())
            } else {
                kakaoSignUp(
                    getCustomKakaoId(),
                    getCustomKakaoSignUpEmail(),
                    getCustomKakaoId()
                )
            }
        }
    }

    fun getCustomKakaoSignUpEmail(): String {
        val kakaoCustomEmail = getCustomKakaoId() + "@kakao.com"
        return kakaoCustomEmail
    }

    fun kakaoLogin(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val oAuthToken = UserApiClient.loginWithKakao(context)
            checkKakaoSocialLogin() {
                isKakaoSocialIdExist()
            }
        }

    }

    fun checkKakaoSocialLogin(onSuccess: () -> Unit) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {

            } else if (user != null) {
                kakaoEmail = user.kakaoAccount?.email.toString()
                kakaoAccount = user.id.toString()
                kakaoNickname = user.kakaoAccount?.profile?.nickname.toString()
                onSuccess()
            }
        }
    }

    fun getCustomKakaoId(): String {
        return kakaoSignUpId + kakaoAccount
    }

    fun getCustomGoogleId(account: String): String {
        return googleSignUpId + account
    }

    fun getCustomNaverId(account: String): String {
        return naverSignUpId + account.substring(0 until 8)
    }

    fun naverSignUp(account: String, email: String, nickname: String) {
        val signUpReq = SignUpReq(account, email, nickname, BuildConfig.social_login_password)
        viewModelScope.launch {
            runCatching {
                repository.postSignUp(signUpReq)
            }.onSuccess {
                if (it?.account != null) {
                    _isNaverSignUpSuccess.value = true
                } else socialLogin(account)
            }.onFailure {
            }
        }
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
