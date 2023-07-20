package com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
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
    fun getUserProfile() {
        account.value = repository.getAccount()
        nickname.value = repository.getNickname()
        profileFollowers.value = repository.getFollowers()
        profileImage.value = repository.getProfileImage()
        textLength.value = repository.getNickname().length.toString()
    }

    fun setTextLength(length: String) {
        textLength.value = length
    }

    fun setProfileImage(image: String) {
        profileImage.value = image
    }
    fun uploadProfileImg() {
        viewModelScope.launch(ceh) {
            var result = repository.editUserProfileImage(profileImage.value!!)
            Log.e("tag", result.toString())
        }
    }
}
