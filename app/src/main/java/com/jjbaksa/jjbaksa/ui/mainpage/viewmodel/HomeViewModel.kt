package com.jjbaksa.jjbaksa.ui.mainpage.viewmodel

import android.Manifest
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    lateinit var fusedLocationProvider: FusedLocationProvider

    private val _userLocation = SingleLiveEvent<UserLocation>()
    val userLocation: SingleLiveEvent<UserLocation> get() = _userLocation

    fun getLocation(latitude: Double?, longitude: Double?, isCameraUpdate: Boolean) {
        _userLocation.value = UserLocation(latitude, longitude, isCameraUpdate)
    }

    fun requestLocation() {
        fusedLocationProvider.requestLastLocation()
        fusedLocationProvider.startLocationUpdates()
    }
}
