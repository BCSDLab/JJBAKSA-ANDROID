package com.jjbaksa.jjbaksa.util

import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class FusedLocationUtil(
    private val context: Context,
    private val callBackLocation: (Double, Double) -> Unit
) {
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(context) }
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            for (location in locationResult.locations) {

                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    Log.e("service", "$latitude / $longitude")
                    callBackLocation(latitude, longitude)
                }
            }
        }
    }
    fun startLocationUpdate() {
        if (context.hasPermission(permissions)) {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, Looper.getMainLooper())
        } else {
        }
    }
    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10 * 1000
            fastestInterval = 5 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
