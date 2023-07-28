package com.jjbaksa.jjbaksa.ui.mainpage.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val repository: HomeRepository
) : BaseViewModel() {

    lateinit var fusedLocationProvider: FusedLocationProvider

    private val _userLocation = SingleLiveEvent<UserLocation>()
    val userLocation: SingleLiveEvent<UserLocation> get() = _userLocation

    fun getLocation(latitude: Double?, longitude: Double?, isCameraUpdate: Boolean) {
        _userLocation.value = UserLocation(latitude, longitude, isCameraUpdate)
    }

    fun requestLocation() {
        viewModelScope.launch {
            fusedLocationProvider.requestLastLocation()
            fusedLocationProvider.startLocationUpdates()
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
