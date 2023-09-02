package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.jjbaksa.domain.model.mainpage.JjCategory
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.dialog.BasicDialog
import com.jjbaksa.jjbaksa.model.ShopContent
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.ui.pin.PinActivity
import com.jjbaksa.jjbaksa.ui.search.SearchActivity
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.hasPermission
import com.jjbaksa.jjbaksa.view.JjMarker
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
import ted.gun0912.clustering.naver.TedNaverClustering

@AndroidEntryPoint
class NaviHomeFragment : BaseFragment<FragmentNaviHomeBinding>(), OnMapReadyCallback {
    override val layoutId: Int
        get() = R.layout.fragment_navi_home

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var currentMap: NaverMap
    private lateinit var mapOptions: NaverMapOptions
    private lateinit var cameraUpdate: CameraUpdate
    private var locationOverlay: LocationOverlay? = null

    private lateinit var tedNaverClusteringBuilder: TedNaverClustering<ShopContent>

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
                BasicDialog(
                    getString(R.string.location_service_term_text),
                    getString(R.string.cancel),
                    getString(R.string.move_setting)
                ) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", requireContext().packageName, null)
                    }
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        startActivity(intent)
                    }
                }.show(parentFragmentManager, LOCATION_PERM_DIALOG)
            }
        }
    }

    private val pinActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_CANCELED) {
                showSnackBar(it.data?.getStringExtra("msg") ?: return@registerForActivityResult)
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
        binding.categoryNearStoreTextView.setOnClickListener {
            viewModel.setCategory(JjCategory.NEAR_STORE)
        }
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
        viewModel.mapShops.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                clearTedNaverClusteringMarkers()
                showSnackBar(getString(R.string.not_find_restaurant))
            }
            addTedNaverClusteringMarkers(it)
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
                JjCategory.NEAR_STORE -> {
                    updateCategoryColorState(binding.categoryNearStoreTextView, true)
                    updateCategoryColorState(binding.categoryFriendTextView, false)
                    updateCategoryColorState(binding.categoryBookmarkTextView, false)
                    getShops()
                }

                JjCategory.FRIEND -> {
                    updateCategoryColorState(binding.categoryNearStoreTextView, false)
                    updateCategoryColorState(binding.categoryFriendTextView, true)
                    updateCategoryColorState(binding.categoryBookmarkTextView, false)
                    getShops()
                }

                JjCategory.BOOKMARK -> {
                    updateCategoryColorState(binding.categoryNearStoreTextView, false)
                    updateCategoryColorState(binding.categoryFriendTextView, false)
                    updateCategoryColorState(binding.categoryBookmarkTextView, true)
                    getShops()
                }
            }
        }
        viewModel.toastMsg.observe(viewLifecycleOwner) { msg ->
            showSnackBar(msg)
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
        initTedNaverClustering()

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

    private fun initTedNaverClustering() {
        tedNaverClusteringBuilder =
            TedNaverClustering.with<ShopContent>(requireContext(), currentMap)
                .customCluster { clusterItem ->
                    JjMarker(requireContext()).apply {
                        setMarkerCount(clusterItem.items.size.toString())
                    }
                }
                .customMarker { item ->
                    Marker().apply {
                        captionHaloColor =
                            ContextCompat.getColor(requireContext(), R.color.color_ffffff)
                        captionTextSize = 12f
                        captionText = item.name
                        icon = OverlayImage.fromView(
                            JjMarker(requireContext()).apply {
                                setImageUrl(item.photo)
                            }
                        )
                    }
                }
                .markerClickListener {
                    val intent = Intent(requireContext(), PinActivity::class.java).apply {
                        putExtra("place_id", it.placeId)
                    }
                    pinActivityResult.launch(intent)
                }
                .make()
    }

    private fun addTedNaverClusteringMarkers(shopList: List<ShopContent>) {
        if (shopList.isNotEmpty()) {
            initTedNaverClustering()
            tedNaverClusteringBuilder.addItems(shopList)
        }
    }

    private fun clearTedNaverClusteringMarkers() {
        tedNaverClusteringBuilder.clearItems()
    }

    private fun currentCameraPosition(lat: Double, lon: Double) {
        cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(lat, lon), 17.0)
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
        viewModel.category.postValue(null)
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
            clearTedNaverClusteringMarkers()
            getShops()
        } else {
            locationPermissionsResult.launch(locationPermissions)
        }
    }

    private fun getShops() {
        when (viewModel.category.value) {
            JjCategory.NEAR_STORE -> {
                viewModel.getMapShop(
                    0, 1, 0,
                    currentMap.cameraPosition.target.latitude,
                    currentMap.cameraPosition.target.longitude
                )
            }

            JjCategory.FRIEND -> {
                viewModel.getMapShop(
                    1, 0, 0,
                    currentMap.cameraPosition.target.latitude,
                    currentMap.cameraPosition.target.longitude
                )
            }

            JjCategory.BOOKMARK -> {
                viewModel.getMapShop(
                    0, 0, 1,
                    currentMap.cameraPosition.target.latitude,
                    currentMap.cameraPosition.target.longitude
                )
            }

            else -> {
                showSnackBar(getString(R.string.choose_restaurant_options))
            }
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
