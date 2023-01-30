package com.jjbaksa.jjbaksa.ui.mainpage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.airbnb.lottie.model.Marker
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentNaverMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons

class NaverMapFragment(): Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_naver_map, container, false) as ViewGroup
        mapView = rootView.findViewById<View>(R.id.naver_map) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(naverMap: NaverMap) {
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.566, 126.978), 10.0)) // 카메라 위치(위도,경도,줌)
            .mapType(NaverMap.MapType.Basic) // 지도 유형
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING) // 빌딩 표시
        MapFragment.newInstance(options)

        val marker = com.naver.maps.map.overlay.Marker() // 마커 찍기
        marker.position = LatLng(37.566, 126.978)
        marker.map = naverMap

        marker.captionText = "2"
        marker.setCaptionAligns(Align.Top)
        marker.captionColor = resources.getColor(R.color.color_ffffff)
        marker.captionTextSize = 13f
        marker.captionOffset = -60

        marker.width = 105
        marker.height = 100

        marker.icon = OverlayImage.fromResource(R.drawable.map_marker_icon)
        marker.iconTintColor = resources.getColor(R.color.color_ff7f23)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}