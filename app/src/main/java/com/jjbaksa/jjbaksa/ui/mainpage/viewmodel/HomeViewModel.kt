package com.jjbaksa.jjbaksa.ui.mainpage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.model.mainpage.JjCategory
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.domain.resp.map.MapShopContent
import com.jjbaksa.domain.usecase.map.GetMapShopUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val repository: HomeRepository,
    private val getMapShopUseCase: GetMapShopUseCase
) : BaseViewModel() {
    val location = MutableLiveData<UserLocation>()
    val lastLocation = MutableLiveData<UserLocation>()
    val category = SingleLiveEvent<JjCategory>()
    val moveCamera = MutableLiveData<Boolean>(true)
    val searchCurrentPosition = MutableLiveData<Boolean>()
    val lastMapMarkers = SingleLiveEvent<List<Marker>>()

    private val _mapShops = SingleLiveEvent<List<MapShopContent>>()
    val mapShops: SingleLiveEvent<List<MapShopContent>> get() = _mapShops

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
            getMapShopUseCase.invoke(optionsFriend, optionsNearby, optionsScrap, lat, lng)
                .collect {
                    it.onSuccess {
                        _mapShops.value = it.mapShopContent
                    }
                        .onFailure { it.printStackTrace() }
                }
        }
    }

    fun getMyInfo() {
        viewModelScope.launch {
            runCatching {
                userRepository.me()
            }.onSuccess {
                when (it) {
                    is RespResult.Success -> {
                        MyInfo.autoLogin = repository.getMyInfoAutoLogin()
                        MyInfo.account = repository.getMyInfoAccount()
                        MyInfo.nickname = repository.getMyInfoNickname()
                        MyInfo.followers = repository.getMyInfoFollowers()
                        MyInfo.profileImage = repository.getMyInfoProfileImage()
                        MyInfo.token = repository.getMyInfoToken()
                    }

                    is RespResult.Error -> {
                    }
                }
            }.onFailure {
            }
        }
    }
}
