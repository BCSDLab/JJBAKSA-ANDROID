package com.jjbaksa.data.datasource.remote

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jjbaksa.data.datasource.LocationDataSource
import com.jjbaksa.domain.resp.location.GPSData
import kotlinx.coroutines.delay
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(
    private val context: Context
) : LocationDataSource {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: GPSData

    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setWaitForAccurateLocation(true)
        .setMinUpdateIntervalMillis(500)
        .setMaxUpdateDelayMillis(1000)
        .build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                currentLocation = GPSData(location.latitude, location.longitude)
                break
            }
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): GPSData {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        while (true) {
            if (this@LocationRemoteDataSource::currentLocation.isInitialized) {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                return currentLocation
            } else {
                delay(1000)
            }
        }
    }
}
