package com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.resp.scrap.UserScrapsShop
import com.jjbaksa.domain.usecase.review.ReviewUseCase
import com.jjbaksa.domain.usecase.scrap.GetShopScrapUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val repository: UserRepository,
    private val scrapUseCase: GetShopScrapUseCase,
    private val reviewUseCase: ReviewUseCase
) : BaseViewModel() {
    val account = MutableLiveData<String>("")
    val nickname = MutableLiveData<String>("")
    val profileFollowers = MutableLiveData<Int>(0)
    val profileImage = MutableLiveData<String>("")
    val textLength = MutableLiveData<String>("")
    val bookmarkHasMore = SingleLiveEvent<Boolean>()

    private val _loadImage = SingleLiveEvent<String>()
    val loadImage: LiveData<String> get() = _loadImage

    private val _isResult = SingleLiveEvent<Boolean>()
    val isResult: LiveData<Boolean> get() = _isResult

    private val _scrapsShops = SingleLiveEvent<List<UserScrapsShop>>()
    val scrapsShops: SingleLiveEvent<List<UserScrapsShop>> get() = _scrapsShops

    private val _reviewShops = SingleLiveEvent<ReviewShop>()
    val reviewShops: SingleLiveEvent<ReviewShop> get() = _reviewShops

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

    fun getScraps(user: Int?, cursor: Int?, size: Int) {
        viewModelScope.launch(ceh) {
            scrapUseCase.getUserScrapsShop(user, cursor, size).collect {
                it.onSuccess {
                    _scrapsShops.value = it.content
                    bookmarkHasMore.value = it.content.count() == 10
                }
            }
        }
    }

    fun getReviewShop(cursor: Int?, size: Int) {
        viewModelScope.launch(ceh) {
            reviewUseCase.getReviewShop(cursor, size).collect {
                it.onSuccess {
                    _reviewShops.value = it
                }
            }
        }
    }
}
