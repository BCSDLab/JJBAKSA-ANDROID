package com.jjbaksa.jjbaksa.util

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import com.jjbaksa.domain.model.mainpage.UserLocation

class FusedLocationUtil(
    private val context: Context,
    private val callBackLocation: (Double, Double) -> Unit = { _,_ -> }
) {
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            context
        )
    }
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
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

    private fun checkPermission(): Boolean = context.hasPermission(permissions)

    fun startLocationUpdate() {
        if (checkPermission()) {
            fusedLocationClient.requestLocationUpdates(
                createLocationRequest(),
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
        }
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY, 5 * 1000)
            .build()
    }

    fun getLastLocation(): Task<Location>? {
        return if (checkPermission()) {
            fusedLocationClient.lastLocation
        } else {
            null
        }
    }


    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}

