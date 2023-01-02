package com.jjbaksa.jjbaksa.ui.social

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
import com.jjbaksa.jjbaksa.BuildConfig
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val kakaoSignUpId = "kakao"
    val googleSignUpId = "google"
    val naverSignUpId = "naver"

    companion object {
        const val TAG = "SocialLogin"
    }

    suspend fun checkSocialIdExist(account: String): Boolean {
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
                Log.i(TAG, "kakao postSignUp 실패")
            }
        }
    }

    fun isKakaoSocialIdExist(kakaoAccount: String) {
        viewModelScope.launch {
            if (checkSocialIdExist(getCustomKakaoId(kakaoAccount))) {
                socialLogin(getCustomKakaoId(kakaoAccount))
            } else {
                kakaoSignUp(
                    getCustomKakaoId(kakaoAccount),
                    getCustomKakaoSignUpEmail(kakaoAccount),
                    getCustomKakaoId(kakaoAccount)
                )
            }
        }
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
                Log.i(TAG, "test naver postSignUp 실패")
            }
        }
    }

    fun getCustomKakaoSignUpEmail(value: String): String{
        val kakaoCustomEmail = getCustomKakaoId(value) + "@kakao.com"
        return kakaoCustomEmail
    }

    fun getCustomKakaoId(account: String): String {
        return kakaoSignUpId + account
    }

    fun getCustomGoogleId(account: String): String {
        return googleSignUpId + account
    }

    fun getCustomNaverId(account: String): String {
        return naverSignUpId + account.substring(0 until 8)
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
}
