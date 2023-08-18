package com.jjbaksa.jjbaksa.model

import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

data class ShopContent(
    val placeId: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val photo: String = ""
) : TedClusterItem {
    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(this.lat, this.lng)
    }
}
