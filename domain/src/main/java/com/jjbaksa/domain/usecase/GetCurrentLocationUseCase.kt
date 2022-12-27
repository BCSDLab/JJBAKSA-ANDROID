package com.jjbaksa.domain.usecase

import com.jjbaksa.domain.repository.LocationRepository
import com.jjbaksa.domain.resp.location.GPSData
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): GPSData {
        return locationRepository.getLocation()
    }
}
