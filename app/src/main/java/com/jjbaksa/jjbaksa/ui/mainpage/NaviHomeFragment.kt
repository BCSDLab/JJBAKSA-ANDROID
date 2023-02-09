package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationServices
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.ui.mainpage.sub.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class NaviHomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentNaviHomeBinding

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var homeAlertDialog: HomeAlertDialog
    private lateinit var fusedLocationProvider: FusedLocationProvider

    private var naverMapOptions: NaverMapOptions? = null
    private var currentNaverMap: NaverMap? = null
    private var currentLocationOverlay: LocationOverlay? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_navi_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProvider = FusedLocationProvider(requireContext(), homeViewModel)

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)
        observeData()
        with(binding.buttonCheckLocation) {
            setOnClickListener {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        homeViewModel.locationPermissions[0]
                    ) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                        context,
                        homeViewModel.locationPermissions[1]
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    imageTintList = ColorStateList.valueOf(Color.rgb(196, 196, 196))
                    fusedLocationProvider.requestLastLocation()
                    fusedLocationProvider.startLocationUpdates()
                } else {
                    showPermissionInfoDialog()
                }
            }
        }

        with(binding.buttonNearbyRestaurant) {
            setOnClickListener {
            }
        }
        with(binding.buttonFriendRestaurant) {
            setOnClickListener {
            }
        }
        with(binding.buttonBookmarkRestaurant) {
            setOnClickListener {
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        currentNaverMap = naverMap
        naverMapOptions = NaverMapOptions()
            .mapType(NaverMap.MapType.Basic)
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)

        MapFragment.newInstance(naverMapOptions)
        fusedLocationProvider.requestLastLocation()

        val uiSettings = currentNaverMap?.uiSettings
        uiSettings?.isCompassEnabled = false
        uiSettings?.logoGravity = Gravity.CENTER

        binding.naverMapCompassView.map = currentNaverMap

        naverMap.addOnCameraChangeListener { reason, _ ->
            when (reason) {
                -1, -2 -> {
                    binding.buttonCheckLocation.imageTintList =
                        ColorStateList.valueOf(Color.rgb(255, 127, 35))
                }
            }
        }
    }

    private fun showPermissionInfoDialog() {
        homeAlertDialog = HomeAlertDialog()
        homeAlertDialog.show(childFragmentManager, "")
    }

    private fun observeData() {
        homeViewModel.userLocation.observe(
            viewLifecycleOwner,
            Observer<UserLocation> {
                if (it.currentLatitude != null) {
                    val cameraUpdate =
                        CameraUpdate.scrollTo(LatLng(it.currentLatitude!!, it.currentLongitude!!))
                    currentNaverMap?.moveCamera(cameraUpdate)

                    currentLocationOverlay = currentNaverMap?.locationOverlay
                    currentLocationOverlay?.icon =
                        OverlayImage.fromResource(R.drawable.shape_circ_3d93f8_stroke_ffffff)
                    currentLocationOverlay?.circleRadius = 100
                    currentLocationOverlay?.position = LatLng(
                        it.currentLatitude!!,
                        it.currentLongitude!!
                    )
                    currentLocationOverlay?.isVisible = true
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        naverMapOptions = null
        currentNaverMap = null
        currentLocationOverlay = null
        fusedLocationProvider.stopLocationUpdates()
    }

    companion object {
        fun newInstance(): NaviHomeFragment {
            val args = Bundle().apply {
            }
            val fragment = NaviHomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
