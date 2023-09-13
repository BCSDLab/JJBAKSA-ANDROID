package com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.review.MyReviewShops
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.model.review.ReviewShop
import com.jjbaksa.domain.model.scrap.ScrapsContent
import com.jjbaksa.domain.usecase.review.ReviewUseCase
import com.jjbaksa.domain.usecase.scrap.GetShopScrapUseCase
import com.jjbaksa.domain.usecase.user.UserUseCase
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
    private val reviewUseCase: ReviewUseCase,
    private val userUseCase: UserUseCase
) : BaseViewModel() {
    val account = MutableLiveData<String>("")
    val nickname = MutableLiveData<String>("")
    val profileFollowers = MutableLiveData<Int>(0)
    val profileImage = MutableLiveData<String>("")
    val textLength = MutableLiveData<String>("")
    val bookmarkHasMore = SingleLiveEvent<Boolean>()
    val placeId = SingleLiveEvent<String>()
    val myReviewHasMore = SingleLiveEvent<Boolean>()

    private val _loadImage = SingleLiveEvent<String>()
    val loadImage: LiveData<String> get() = _loadImage

    private val _isResult = SingleLiveEvent<Boolean>()
    val isResult: LiveData<Boolean> get() = _isResult

    private val _scrapsShops = SingleLiveEvent<List<ScrapsContent>>()
    val scrapsShops: SingleLiveEvent<List<ScrapsContent>> get() = _scrapsShops

    private val _reviewShops = SingleLiveEvent<ReviewShop>()
    val reviewShops: SingleLiveEvent<ReviewShop> get() = _reviewShops

    private val _reviewShopDetail = SingleLiveEvent<MyReviewShops>()
    val reviewShopDetail: SingleLiveEvent<MyReviewShops> get() = _reviewShopDetail

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

    fun setNewNickname(newNickname: String) {
        viewModelScope.launch(ceh) {
            userUseCase.setNewNickname(newNickname).collect {
                it.onSuccess {
                    nickname.value = it.nickname
                    MyInfo.nickname = it.nickname
                    _isResult.value = true
                }
            }
        }
    }

    fun uploadProfileImgAndNickname(image: String, newNickname: String) {
        viewModelScope.launch(ceh) {
            userUseCase.editUserProfile(image).collect {
                it.onSuccess {
                    profileImage.value = it.profileImage.url.toString()
                    MyInfo.profileImage = it.profileImage.url.toString()
                    userUseCase.setNewNickname(newNickname).collect {
                        it.onSuccess {
                            nickname.value = it.nickname
                            MyInfo.nickname = it.nickname
                            _isResult.value = true
                        }
                    }
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

    fun getReviewShopDetail(
        placeId: String,
        idCursor: Int? = null,
        dateCursor: String? = null,
        rateCursor: Int? = null,
        size: Int? = null, // 1~10
        direction: String? = null, // asc / desc
        sort: String? = null // createdAt / rate
    ) {
        viewModelScope.launch(ceh) {
            reviewUseCase.getMyReview(placeId, idCursor, dateCursor, rateCursor, size, direction, sort)
                .collect {
                    it.onSuccess {
                        myReviewHasMore.value = it.content.count() == 10
                        _reviewShopDetail.value = it
                    }.onFailure {
                        _reviewShopDetail.value = MyReviewShops()
                    }
                }
        }
    }
}
