package com.jjbaksa.jjbaksa.ui.mainpage.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.mainpage.JjCategory
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.usecase.shop.ShopUseCase
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.model.ShopContent
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import com.jjbaksa.jjbaksa.util.toShopContent
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val userUseCase: UserUseCase,
    private val shopUseCase: ShopUseCase,
) : BaseViewModel() {
    val location = MutableLiveData<UserLocation>()
    val lastLocation = MutableLiveData<UserLocation>()

    val selectedNearbyStoreCategory = MutableLiveData<Boolean>()
    val selectedFriendCategory = MutableLiveData<Boolean>()
    val selectedBookmarkCategory = MutableLiveData<Boolean>()

    val moveCamera = MutableLiveData<Boolean>(true)
    val searchCurrentPosition = MutableLiveData<Boolean>()

    private val _mapShops = MutableSharedFlow<List<ShopContent>>()
    val mapShops: SharedFlow<List<ShopContent>> get() = _mapShops.asSharedFlow()

    fun setLatLng(lat: Double, lng: Double) {
        location.value = UserLocation(lat, lng)
    }

    fun setLastLocation(lat: Double, lng: Double) {
        lastLocation.value = UserLocation(lat, lng)
    }

    fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double,
    ) {
        viewModelScope.launch(ceh) {
            shopUseCase.invoke(optionsFriend, optionsNearby, optionsScrap, lat, lng) { msg ->
                toastMsg.postValue(msg)
            }.collect {
                it.onSuccess {
                    _mapShops.emit(it.shopsMapsContent.map { it.toShopContent() })
                }
            }
        }
    }

    fun getMyInfo() {
        viewModelScope.launch(ceh) {
            userUseCase.getUserMe().collect {
                it.onSuccess {
                    MyInfo.autoLogin = homeRepository.getMyInfoAutoLogin()
                    MyInfo.account = homeRepository.getMyInfoAccount()
                    MyInfo.nickname = homeRepository.getMyInfoNickname()
                    MyInfo.followers = homeRepository.getMyInfoFollowers()
                    MyInfo.reviews = homeRepository.getMyInfoReviews()
                    MyInfo.profileImage = homeRepository.getMyInfoProfileImage()
                    MyInfo.token = homeRepository.getMyInfoToken()
                    getKakaoInfo()
                }
            }
        }
    }

    fun getKakaoInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Timber.tag("kakao_user_error").e(error)
            } else if (user != null) {
                if (homeRepository.getMyInfoAccount().isEmpty()) MyInfo.account =
                    user.kakaoAccount?.email.toString()
                if (homeRepository.getMyInfoProfileImage().isEmpty()) MyInfo.profileImage =
                    user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                if (homeRepository.getMyInfoNickname().isEmpty()) {
                    MyInfo.nickname = user.kakaoAccount?.profile?.nickname.toString()
                }
            }
        }
    }

    fun clearDataStoreNoneAutoLogin() {
        runBlocking {
            userUseCase.clearDataStoreNoneAutoLogin()
        }
    }
}
