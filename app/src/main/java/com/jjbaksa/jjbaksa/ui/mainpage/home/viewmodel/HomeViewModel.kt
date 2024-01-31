package com.jjbaksa.jjbaksa.ui.mainpage.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.usecase.shop.ShopUseCase
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.model.ShopContent
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.toShopContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
                    val newShops = it.shopsMapsContent
                    _mapShops.emit(newShops.map { it.toShopContent() })
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
                }
            }
        }
    }

    fun clearDataStoreNoneAutoLogin() {
        if (!homeRepository.getMyInfoAutoLogin()) {
            runBlocking {
                userUseCase.logout()
            }
        }
    }
}
