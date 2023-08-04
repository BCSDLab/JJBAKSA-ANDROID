package com.jjbaksa.jjbaksa.ui.mainpage

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.ui.search.SearchActivity
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.LocationOverlay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaviHomeFragment : BaseFragment<FragmentNaviHomeBinding>(), OnMapReadyCallback {
    override val layoutId: Int
        get() = R.layout.fragment_navi_home

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var currentMap: NaverMap
    private lateinit var mapOption: NaverMapOptions
    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var locationOverlay: LocationOverlay

    override fun initView() {
//        val fm = childFragmentManager
//        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
//            ?: MapFragment.newInstance().also {
//                fm.beginTransaction().add(R.id.map, it).commit()
//            }
//        mapFragment.getMapAsync(this)
    }

    override fun initEvent() {
        search()
    }

    private fun search() {
        binding.editTextMainPage.setOnClickListener {
            Intent(requireContext(), SearchActivity::class.java).run { startActivity(this) }
        }
    }

    override fun subscribe() {
//        homeViewModel.userLocation.observe(
//            viewLifecycleOwner,
//            Observer<UserLocation> {
//                if (it.currentLatitude != null) {
//                    if (it.updateCamera) {
//                        val cameraUpdate =
//                            CameraUpdate.scrollTo(
//                                LatLng(
//                                    it.currentLatitude!!,
//                                    it.currentLongitude!!
//                                )
//                            )
//                        currentMap?.moveCamera(cameraUpdate)
//                    }
//                    currentLocationOverlay = currentMap?.locationOverlay
//                    currentLocationOverlay?.icon =
//                        OverlayImage.fromResource(R.drawable.shape_circ_3d93f8_stroke_ffffff)
//                    currentLocationOverlay?.circleRadius = 100
//                    currentLocationOverlay?.position = LatLng(
//                        it.currentLatitude!!,
//                        it.currentLongitude!!
//                    )
//                    currentLocationOverlay?.isVisible = true
//                }
//            }
//        )
    }


    override fun onMapReady(naverMap: NaverMap) {
//        currentMap = naverMap
//        mapOptions = NaverMapOptions()
//            .mapType(NaverMap.MapType.Basic)
//            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)
//        MapFragment.newInstance(mapOptions)
//
//        currentMap?.setContentPadding(0, 0, 0, 0)
//
//        val uiSettings = currentMap?.uiSettings
//        uiSettings?.isCompassEnabled = false
//        uiSettings?.isZoomControlEnabled = false
//        binding.naverMapCompassView.map = currentMap
//
//        naverMap.addOnCameraChangeListener { reason, _ ->
//            when (reason) {
//                -1, -2 -> {
//                    binding.buttonCheckLocation.imageTintList = ColorObject.ColorFF7F23
//                    binding.buttonCheckTheRealLocation.isVisible = true
//                }
//            }
//        }
    }



    fun zoomIn() {
        cameraUpdate = CameraUpdate.zoomIn()
        currentMap.moveCamera(cameraUpdate)
    }

    fun zoomOut() {
        cameraUpdate = CameraUpdate.zoomOut()
        currentMap.moveCamera(cameraUpdate)
    }
}
