package com.jjbaksa.data.model.map

import com.google.gson.annotations.SerializedName
import com.jjbaksa.data.model.search.LocationBody

data class GeometryDTO(
    @SerializedName("location")
    val location: LocationBody? = LocationBody(0.0, 0.0)
)
