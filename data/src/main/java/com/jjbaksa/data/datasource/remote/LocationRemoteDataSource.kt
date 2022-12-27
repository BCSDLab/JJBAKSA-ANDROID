package com.jjbaksa.data.datasource.remote

import android.content.Context
import com.jjbaksa.data.datasource.LocationDataSource
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(
    private val context: Context
) : LocationDataSource {
}
