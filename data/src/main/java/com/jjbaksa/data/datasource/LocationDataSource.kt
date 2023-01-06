package com.jjbaksa.data.datasource

import com.jjbaksa.domain.resp.location.GPSData

interface LocationDataSource {
    suspend fun getLocation(): GPSData
}
