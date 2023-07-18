package com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjbaksa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val repository: UserRepository
) : ViewModel() {
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
}
