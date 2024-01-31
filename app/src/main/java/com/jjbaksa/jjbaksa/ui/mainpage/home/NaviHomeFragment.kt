package com.jjbaksa.jjbaksa.ui.mainpage.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.dialog.BasicDialog
import com.jjbaksa.jjbaksa.model.ShopContent
import com.jjbaksa.jjbaksa.ui.mainpage.home.viewmodel.HomeViewModel
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ted.gun0912.clustering.naver.TedNaverClustering

@AndroidEntryPoint
class NaviHomeFragment : BaseFragment<FragmentNaviHomeBinding>(), OnMapReadyCallback {
    override val layoutId: Int
        get() = R.layout.fragment_navi_home

    private val viewModel: HomeViewModel by activityViewModels()
    private var currentMap: NaverMap? = null
    private lateinit var mapOptions: NaverMapOptions
    private lateinit var cameraUpdate: CameraUpdate
    private var locationOverlay: LocationOverlay? = null

    private var tedNaverClusteringBuilder: TedNaverClustering<ShopContent>? = null

    private var backClickTime = 0L

    override var onBackPressedCallBack: OnBackPressedCallback? =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backClickTime >= 2000L) {
                    backClickTime = System.currentTimeMillis()
                    showSnackBar(getString(R.string.back_finish))
                } else {
                    requireActivity().finish()
                }
            }
        }

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
        binding.lifecycleOwner = this
        binding.view = this
        binding.vm = viewModel
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
            viewModel.selectedNearbyStoreCategory.value =
                viewModel.selectedNearbyStoreCategory.value != true
            getShops()
        }
        binding.categoryFriendTextView.setOnClickListener {
            viewModel.selectedFriendCategory.value = viewModel.selectedFriendCategory.value != true
            getShops()
        }
        binding.categoryBookmarkTextView.setOnClickListener {
            viewModel.selectedBookmarkCategory.value =
                viewModel.selectedBookmarkCategory.value != true
            getShops()
        }
    }

    override fun subscribe() {
        viewModel.lastLocation.observe(viewLifecycleOwner) {
            currentCameraPosition(it.latitude, it.longitude)
            initLocationOverlay(it.latitude, it.longitude)
        }
        viewModel.mapShops.flowWithLifecycle(lifecycle).onEach {
            if (it.isEmpty()) {
                clearTedNaverClusteringMarkers()
                showSnackBar(getString(R.string.not_find_restaurant))
            } else {
                addTedNaverClusteringMarkers(it)
            }
        }.launchIn(lifecycleScope)
        viewModel.location.observe(viewLifecycleOwner) {
            if (locationOverlay == null) return@observe
            initLocationOverlay(it.latitude, it.longitude)
        }
        viewModel.searchCurrentPosition.observe(viewLifecycleOwner) {
            binding.searchCurrentLocationButton.isVisible = it
        }
        viewModel.toastMsg.observe(viewLifecycleOwner) { msg ->
            showSnackBar(msg)
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
        locationOverlay = currentMap?.locationOverlay
        mapOptions = NaverMapOptions()
            .mapType(NaverMap.MapType.Basic)
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)
        initUiSettings()
        initTedNaverClustering()

        fusedLocationUtil.getLastLocation()?.addOnSuccessListener {
            if (it == null) return@addOnSuccessListener
            viewModel.setLastLocation(it.latitude, it.longitude)
        }

        currentMap?.addOnCameraChangeListener { reason, _ ->
            if (reason == -1 && viewModel.moveCamera.value == true) {
                viewModel.searchCurrentPosition.value = true
                viewModel.moveCamera.value = false
            }
        }
    }

    private fun initTedNaverClustering() {
        if (currentMap == null)
            return
        tedNaverClusteringBuilder =
            TedNaverClustering.with<ShopContent>(requireContext(), currentMap!!)
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
            tedNaverClusteringBuilder?.addItems(shopList)
        }
    }

    private fun clearTedNaverClusteringMarkers() {
        tedNaverClusteringBuilder?.clearItems()
    }

    private fun currentCameraPosition(lat: Double, lon: Double) {
        if (currentMap == null)
            return
        cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(lat, lon), 17.0)
            .animate(CameraAnimation.Easing)
        currentMap!!.moveCamera(cameraUpdate)
    }

    private fun initUiSettings() {
        if (currentMap == null)
            return
        currentMap!!.uiSettings.isCompassEnabled = false
        currentMap!!.uiSettings.isZoomControlEnabled = false
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

    override fun onDestroy() {
        viewModel.clearDataStoreNoneAutoLogin()
        super.onDestroy()
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
        if (currentMap == null)
            return

        viewModel.getMapShop(
            optionsNearby = viewModel.selectedNearbyStoreCategory.value?.compareTo(false) ?: 0,
            optionsFriend = viewModel.selectedFriendCategory.value?.compareTo(false) ?: 0,
            optionsScrap = viewModel.selectedBookmarkCategory.value?.compareTo(false) ?: 0,
            lat = currentMap!!.cameraPosition.target.latitude,
            lng = currentMap!!.cameraPosition.target.longitude
        )
    }

    fun zoomIn() {
        if (currentMap == null)
            return
        cameraUpdate = CameraUpdate.zoomIn()
        currentMap!!.moveCamera(cameraUpdate)
    }

    fun zoomOut() {
        if (currentMap == null)
            return
        cameraUpdate = CameraUpdate.zoomOut()
        currentMap!!.moveCamera(cameraUpdate)
    }

    companion object {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        const val LOCATION_PERM_DIALOG = "LOCATION_PERM_DIALOG"
        fun newInstance() = NaviHomeFragment()
        val TAG = "NaviHomeFragment"
    }
}
