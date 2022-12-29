package com.jjbaksa.jjbaksa.ui.social

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.user.LoginResult
import com.jjbaksa.domain.resp.user.SignUpReq
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

    private val _isSignUpSuccess = MutableLiveData<Boolean>(false)
    val isSignUpSuccess: LiveData<Boolean>
        get() = _isSignUpSuccess

    private val _isNaverSignUpSuccess = SingleLiveEvent<Boolean>()
    val isNaverSignUpSuccess: LiveData<Boolean> = _isNaverSignUpSuccess

    private val _isKakaoSignUpSuccess = SingleLiveEvent<Boolean>()
    val isKakaoSignUpSuccess: LiveData<Boolean> = _isKakaoSignUpSuccess

    private val _isGoogleSignUpSuccess = SingleLiveEvent<Boolean>()
    val isGoogleSignUpSuccess: LiveData<Boolean> = _isGoogleSignUpSuccess

    val kakaoSignUpId = "kakao"
    val googleSignUpId = "google"
    val naverSignUpId = "naver"

    companion object {
        const val TAG = "SocialLogin"
    }

    fun onNaverButtonClick() {
        _isNaverSignUpSuccess.call()
    }

    fun onKakaoButtonClick() {
        _isKakaoSignUpSuccess.call()
    }

    fun onGoogleButtonClick() {
        _isGoogleSignUpSuccess.call()
    }

    suspend fun checkSocialIdExist(account: String): Boolean {
        return repository.checkAccountAvailable(account)
    }

    fun login(account: String) {
        Log.i(TAG, "login 함수 실행")
        viewModelScope.launch(ceh) {
            repository.postLogin(account, "signup0000!", false) {
                _loginState.value = it
            }
        }
    }

    fun socialKakaoSignUp(id: String, email: String, nickname: String) {
        Log.i(TAG, "sign up 함수 실행")
        val signUpReq = SignUpReq(id, email, nickname, "signup0000!")
        viewModelScope.launch {
            runCatching {
                repository.postSignUp(signUpReq)
            }.onSuccess {
                Log.i(TAG, "kakao 회원가입 응답 계정 : ${it?.account}, 응답 이메일 : ${it?.email} ${it}")
                if (it?.account != null) {
                    _isKakaoSignUpSuccess.value = true
                } else login(id)

            }.onFailure {
                Log.i(TAG, "kakao postSignUp 실패")
            }
        }
    }

    fun isKakaoSocialIdExist(kakaoAccount: String){
        viewModelScope.launch {
            if (checkSocialIdExist(getCustomKakaoId(kakaoAccount))) {
                Log.i(TAG, "아이디 존재")
                login(getCustomKakaoId(kakaoAccount))
            } else {
                // token 으로 회원가입에 필요한 정보 요청 후 회원가입
                Log.i(TAG, "회원가입 요청")
                socialKakaoSignUp(getCustomKakaoId(kakaoAccount),
                    "seongwoorang@naver.com",
                    "nick")
            }
        }
    }

    fun naverSignUp(account: String, email: String, nickname: String) {
        Log.i(TAG, "sign up 함수 실행")
        val signUpReq = SignUpReq(account, email,nickname, "signup0000!")
        viewModelScope.launch {
            runCatching {
                repository.postSignUp(signUpReq)
            }.onSuccess {
                Log.i(TAG, "test naver 회원가입 응답 계정 : ${it?.account}, 응답 이메일 : ${it?.email} ${it}")
                if (it?.account != null) {
                    _isNaverSignUpSuccess.value = true
                } else login(account)

            }.onFailure {
                Log.i(TAG, "test naver postSignUp 실패")
            }
        }
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
}
