package com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val repository: UserRepository
) : BaseViewModel() {
    val account = MutableLiveData<String>("")
    val nickname = MutableLiveData<String>("")
    val profileFollowers = MutableLiveData<Int>(0)
    val profileImage = MutableLiveData<String>("")
    val textLength = MutableLiveData<String>("")

    private val _loadImage = SingleLiveEvent<String>()
    val loadImage: LiveData<String> get() = _loadImage

    private val _isResult = SingleLiveEvent<Boolean>()
    val isResult: LiveData<Boolean> get() = _isResult

    fun getUserProfile() {
        account.value = MyInfo.account
        nickname.value = MyInfo.nickname
        profileFollowers.value = MyInfo.followers
        profileImage.value = MyInfo.profileImage
        textLength.value = MyInfo.nickname.length.toString()
    }

    fun setTextLength(length: String) {
        textLength.value = length
    }

    fun setLoadImage(image: String) {
        _loadImage.value = image
    }

    fun uploadProfileImgAndNickname(image: String, newNickname: String) {
        viewModelScope.launch(ceh) {
            val response = repository.editUserProfileImage(image)
            if (response == RespResult.Success(true)) {
                repository.getProfileImage().also {
                    profileImage.value = it
                    MyInfo.profileImage = it
                }
                val nicknameResponse = repository.setNewNickname(newNickname)
                if (nicknameResponse.isSuccess) {
                    repository.getNickname().also {
                        nickname.value = it
                        MyInfo.nickname = it
                    }
                    _isResult.value = true
                }
            }
        }
    }
}
