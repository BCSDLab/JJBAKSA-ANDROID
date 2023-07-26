package com.jjbaksa.jjbaksa.ui.mainpage.viewmodel

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
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

    fun getUserMe() {
        viewModelScope.launch {
            repository.me()
        }
    }
}
