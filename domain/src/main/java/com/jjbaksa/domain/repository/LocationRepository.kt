package com.jjbaksa.domain.repository

import com.jjbaksa.domain.resp.location.GPSData

interface LocationRepository {
    suspend fun getLocation(): GPSData
}
