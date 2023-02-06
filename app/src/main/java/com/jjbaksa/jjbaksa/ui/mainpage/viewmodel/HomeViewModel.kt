package com.jjbaksa.jjbaksa.ui.mainpage.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.domain.repository.HomeRepository
import com.jjbaksa.domain.repository.UserRepository
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {
    val locationPermission = arrayOf (
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val _userLocation = SingleLiveEvent<UserLocation>()
    val userLocation: SingleLiveEvent<UserLocation> get() = _userLocation

    @SuppressLint("MissingPermission")
    fun loadLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            getLocation(location.latitude, location.longitude)
        }
    }

    private fun getLocation(latitude:Double, longitude:Double){
        _userLocation.value = UserLocation(latitude, longitude)
    }
}

class HomeAlertDialog(): DialogFragment(){
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage(R.string.location_service_term_text)
            setNegativeButton(R.string.cancel, null)
            setPositiveButton(R.string.move_setting) { _, _ ->
                val intent = Intent(Settings.ACTION_SETTINGS)
                if (intent.resolveActivity(requireContext().packageManager) != null){
                    startActivity(intent)
                }
            }
        }.create()
    }
}