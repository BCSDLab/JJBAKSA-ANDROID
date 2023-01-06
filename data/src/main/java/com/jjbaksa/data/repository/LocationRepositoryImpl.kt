package com.jjbaksa.data.repository

import com.jjbaksa.data.datasource.remote.LocationRemoteDataSource
import com.jjbaksa.domain.repository.LocationRepository
import com.jjbaksa.domain.resp.location.GPSData
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteDataSource: LocationRemoteDataSource
) : LocationRepository {
    override suspend fun getLocation(): GPSData {
        return locationRemoteDataSource.getLocation()
    }
}
