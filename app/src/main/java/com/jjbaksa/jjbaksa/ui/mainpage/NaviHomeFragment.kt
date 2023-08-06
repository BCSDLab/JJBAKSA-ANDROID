package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.content.Intent
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.jjbaksa.domain.model.mainpage.JjCategory
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.dialog.PermissionDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.ui.search.SearchActivity
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.hasPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaviHomeFragment : BaseFragment<FragmentNaviHomeBinding>(), OnMapReadyCallback {
    override val layoutId: Int
        get() = R.layout.fragment_navi_home

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var currentMap: NaverMap
    private lateinit var mapOptions: NaverMapOptions
    private lateinit var cameraUpdate: CameraUpdate
    private var locationOverlay: LocationOverlay? = null

    private val fusedLocationUtil: FusedLocationUtil by lazy {
        FusedLocationUtil(
            requireContext(),
            this::locationCallback
        )
    }

    private val locationPermissionsResult = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        when {
            isGranted.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                fusedLocationUtil.startLocationUpdate()
            }

            isGranted.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                fusedLocationUtil.startLocationUpdate()
            }

            else -> {
                // TODO : 위치 권한 거부 시 설정으로 이동 또는 교육용 팝업 띄우기
                PermissionDialog().show(parentFragmentManager, LOCATION_PERM_DIALOG)
            }
        }
    }

    override fun initView() {
        binding.view = this
        initMap()
        checkLocationPermissions()
    }

    private fun initMap() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun initEvent() {
        search()
        selectCategory()
    }

    private fun search() {
        binding.editTextMainPage.setOnClickListener {
            Intent(requireContext(), SearchActivity::class.java).run { startActivity(this) }
        }
    }

    private fun selectCategory() {
        binding.categoryFriendTextView.setOnClickListener {
            viewModel.setCategory(JjCategory.FRIEND)
        }
        binding.categoryBookmarkTextView.setOnClickListener {
            viewModel.setCategory(JjCategory.BOOKMARK)
        }
    }

    override fun subscribe() {
        viewModel.lastLocation.observe(viewLifecycleOwner) {
            currentCameraPosition(it.latitude, it.longitude)
            initLocationOverlay(it.latitude, it.longitude)
        }
        viewModel.location.observe(viewLifecycleOwner) {
            if (locationOverlay == null) return@observe
            initLocationOverlay(it.latitude, it.longitude)
        }
        viewModel.searchCurrentPosition.observe(viewLifecycleOwner) {
            binding.searchCurrentLocationButton.isVisible = it
        }
        viewModel.category.observe(viewLifecycleOwner) { category ->
            when (category) {
                JjCategory.FRIEND -> {
                    updateCategoryColorState(binding.categoryFriendTextView, true)
                    updateCategoryColorState(binding.categoryBookmarkTextView, false)
                }

                JjCategory.BOOKMARK -> {
                    updateCategoryColorState(binding.categoryFriendTextView, false)
                    updateCategoryColorState(binding.categoryBookmarkTextView, true)
                }
            }
        }
        viewModel.mapMarkers.observe(viewLifecycleOwner) {
            Log.e("로그", "markers : $it")
            if (!viewModel.lastMapMarkers.value.isNullOrEmpty()) {
                viewModel.lastMapMarkers.value!!.forEach { marker ->
                    marker.map = null
                }
            }

            // TODO : 마커 중복 해결하기
            if (it.isNotEmpty()) {
                viewModel.lastMapMarkers.value = it

                it.forEachIndexed { index, marker ->
                    marker.map = currentMap
                    marker.captionText = viewModel.mapShops.value?.get(index)?.name ?: ""

                    marker.setOnClickListener {
                        Log.e("로그", "marker position : ${marker.position}")
                        false
                    }
                }
            }
        }
    }

    private fun updateCategoryColorState(categoryView: TextView, state: Boolean) {
        if (state) {
            categoryView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_ff7f23
                )
            )
            categoryView.compoundDrawableTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.color_ff7f23)
        } else {
            categoryView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_666666
                )
            )
            categoryView.compoundDrawableTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.color_666666)
        }
    }

    private fun initLocationOverlay(lat: Double, lon: Double) {
        locationOverlay?.apply {
            this.icon = OverlayImage.fromResource(R.drawable.shape_circ_3d93f8_stroke_ffffff)
            this.circleRadius = 100
            this.position = LatLng(lat, lon)
            this.isVisible = true
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        currentMap = naverMap
        locationOverlay = currentMap.locationOverlay
        mapOptions = NaverMapOptions()
            .mapType(NaverMap.MapType.Basic)
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)
        initUiSettings()

        fusedLocationUtil.getLastLocation()?.addOnSuccessListener {
            if (it == null) return@addOnSuccessListener
            viewModel.setLastLocation(it.latitude, it.longitude)
        }

        currentMap.addOnCameraChangeListener { reason, _ ->
            if (reason == -1 && viewModel.moveCamera.value == true) {
                viewModel.searchCurrentPosition.value = true
                viewModel.moveCamera.value = false
            }
        }
    }

    private fun currentCameraPosition(lat: Double, lon: Double) {
        cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(lat, lon), 18.0)
            .animate(CameraAnimation.Easing)
        currentMap.moveCamera(cameraUpdate)
    }

    private fun initUiSettings() {
        currentMap.uiSettings.isCompassEnabled = false
        currentMap.uiSettings.isZoomControlEnabled = false
        binding.naverMapCompassView.map = currentMap
    }

    private fun checkLocationPermissions() {
        if (requireContext().hasPermission(locationPermissions)) {
            currentCameraPosition(
                viewModel.lastLocation.value?.latitude ?: return,
                viewModel.lastLocation.value?.longitude ?: return
            )
            fusedLocationUtil.startLocationUpdate()
        }
    }

    private fun locationCallback(latitude: Double, longitude: Double) {
        viewModel.setLatLng(latitude, longitude)
    }

    override fun onResume() {
        super.onResume()
        fusedLocationUtil.startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationUtil.stopLocationUpdates()
    }

    fun seeMoreCategory() {
        binding.categoryLinearLayout.isVisible = !binding.categoryLinearLayout.isVisible
        if (binding.categoryLinearLayout.isVisible) {
            binding.buttonSeeMore.imageTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.color_ff7f23)
        } else {
            binding.buttonSeeMore.imageTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.color_666666)
        }
    }

    fun loadLocation() {
        if (requireContext().hasPermission(locationPermissions)) {
            fusedLocationUtil.getLastLocation()?.addOnSuccessListener {
                if (it == null) return@addOnSuccessListener
                viewModel.setLastLocation(it.latitude, it.longitude)
            }
        } else {
            locationPermissionsResult.launch(locationPermissions)
        }
    }

    fun reloadLocation() {
        viewModel.searchCurrentPosition.value = false
        viewModel.moveCamera.value = true
        if (requireContext().hasPermission(locationPermissions)) {
            // TODO: 현재 위치에서 상점 검색
            viewModel.getMapShop(
                0, 1, 0,
                currentMap.cameraPosition.target.latitude,
                currentMap.cameraPosition.target.longitude
            )
        } else {
            locationPermissionsResult.launch(locationPermissions)
        }
    }

    fun zoomIn() {
        cameraUpdate = CameraUpdate.zoomIn()
        currentMap.moveCamera(cameraUpdate)
    }

    fun zoomOut() {
        cameraUpdate = CameraUpdate.zoomOut()
        currentMap.moveCamera(cameraUpdate)
    }

    companion object {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val LOCATION_PERM_DIALOG = "LOCATION_PERM_DIALOG"
    }
}
