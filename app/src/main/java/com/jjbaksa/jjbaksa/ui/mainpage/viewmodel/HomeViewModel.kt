package com.jjbaksa.jjbaksa.ui.mainpage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.mainpage.JjCategory
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.usecase.map.GetMapShopUseCase
import com.jjbaksa.domain.usecase.user.UserUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.model.ShopContent
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import com.jjbaksa.jjbaksa.util.toShopContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val userUseCase: UserUseCase,
    private val getMapShopUseCase: GetMapShopUseCase
) : BaseViewModel() {
    val location = MutableLiveData<UserLocation>()
    val lastLocation = MutableLiveData<UserLocation>()
    val category = SingleLiveEvent<JjCategory>()
    val moveCamera = MutableLiveData<Boolean>(true)
    val searchCurrentPosition = MutableLiveData<Boolean>()

    private val _mapShops = SingleLiveEvent<List<ShopContent>>()
    val mapShops: SingleLiveEvent<List<ShopContent>> get() = _mapShops

    fun setLatLng(lat: Double, lng: Double) {
        location.value = UserLocation(lat, lng)
    }

    fun setLastLocation(lat: Double, lng: Double) {
        lastLocation.value = UserLocation(lat, lng)
    }

    fun setCategory(category: JjCategory) {
        this.category.value = category
    }

    fun getMapShop(
        optionsFriend: Int,
        optionsNearby: Int,
        optionsScrap: Int,
        lat: Double,
        lng: Double
    ) {
        viewModelScope.launch(ceh) {
            getMapShopUseCase.invoke(optionsFriend, optionsNearby, optionsScrap, lat, lng) { msg ->
                toastMsg.postValue(msg)
            }.collect {
                it.onSuccess {
                    _mapShops.value = it.mapShopContent.map { it.toShopContent() }
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
}
