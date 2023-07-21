package com.jjbaksa.jjbaksa.ui.mainpage

import android.content.Intent
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.dialog.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.ui.search.SearchActivity
import com.jjbaksa.jjbaksa.util.ColorObject
import com.jjbaksa.jjbaksa.util.hasPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaviHomeFragment : BaseFragment<FragmentNaviHomeBinding>(), OnMapReadyCallback {
    override val layoutId: Int
        get() = R.layout.fragment_navi_home

    override fun initView() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun initEvent() {
        with(binding) {
            editTextMainPage.setOnClickListener {
                Intent(requireContext(), SearchActivity::class.java).run { startActivity(this) }
            }
        }
    }

    override fun subscribe() {
        homeViewModel.userLocation.observe(
            viewLifecycleOwner,
            Observer<UserLocation> {
                if (it.currentLatitude != null) {
                    if (it.updateCamera) {
                        val cameraUpdate =
                            CameraUpdate.scrollTo(
                                LatLng(
                                    it.currentLatitude!!,
                                    it.currentLongitude!!
                                )
                            )
                        currentMap?.moveCamera(cameraUpdate)
                    }
                    currentLocationOverlay = currentMap?.locationOverlay
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

    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var cameraUpdate: CameraUpdate
    private var mapOptions: NaverMapOptions? = null
    private var currentMap: NaverMap? = null
    private var currentLocationOverlay: LocationOverlay? = null

    private var isVisibleStoreCategory = false
    private var isStoreCategoryFriend = false
    private var isStoreCategoryBookmark = false

    override fun onMapReady(naverMap: NaverMap) {
        currentMap = naverMap
        mapOptions = NaverMapOptions()
            .mapType(NaverMap.MapType.Basic)
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)
        MapFragment.newInstance(mapOptions)

        currentMap?.setContentPadding(0, 0, 0, 0)

        val uiSettings = currentMap?.uiSettings
        uiSettings?.isCompassEnabled = false
        uiSettings?.isZoomControlEnabled = false
        binding.naverMapCompassView.map = currentMap

        naverMap.addOnCameraChangeListener { reason, _ ->
            when (reason) {
                -1, -2 -> {
                    binding.buttonCheckLocation.imageTintList = ColorObject.ColorFF7F23
                    binding.buttonCheckTheRealLocation.isVisible = true
                }
            }
        }
    }

    fun checkLocation() {
        if (setLocationPermission()) {
            homeViewModel.requestLocation()
            binding.buttonCheckLocation.imageTintList = ColorObject.Color666666
        } else {
            HomeAlertDialog().show(parentFragmentManager, DIALOG_TAG)
        }
    }

    fun onClickFriendStoreCategory() {
        if (!isStoreCategoryBookmark) {
            if (isStoreCategoryFriend) {
                setIconColorAndTextColor(
                    binding.storeCategoryFriendImageView,
                    binding.storeCategoryFriendTextView,
                    ColorObject.Color666666
                )
            } else {
                setIconColorAndTextColor(
                    binding.storeCategoryFriendImageView,
                    binding.storeCategoryFriendTextView,
                    ColorObject.ColorFF7F23
                )
            }
            isStoreCategoryFriend = !isStoreCategoryFriend
        }
    }

    fun onClickBookmarkStoreCategory() {
        if (!isStoreCategoryFriend) {
            if (isStoreCategoryBookmark) {
                setIconColorAndTextColor(
                    binding.storeCategoryBookmarkImageView,
                    binding.storeCategoryBookmarkTextView,
                    ColorObject.Color666666
                )
            } else {
                setIconColorAndTextColor(
                    binding.storeCategoryBookmarkImageView,
                    binding.storeCategoryBookmarkTextView,
                    ColorObject.ColorFF7F23
                )
            }
            isStoreCategoryBookmark = !isStoreCategoryBookmark
        }
    }

    fun seeMoreStoreCategory() {
        isVisibleStoreCategory = !isVisibleStoreCategory
        binding.storeCategoryContainer.isVisible = isVisibleStoreCategory
        if (isVisibleStoreCategory) {
            binding.buttonSeeMore.imageTintList = ColorObject.ColorFF7F23
        } else {
            binding.buttonSeeMore.imageTintList = ColorObject.Color666666
        }
    }

    fun zoomIn() {
        cameraUpdate = CameraUpdate.zoomIn()
        currentMap?.moveCamera(cameraUpdate)
    }

    fun zoomOut() {
        cameraUpdate = CameraUpdate.zoomOut()
        currentMap?.moveCamera(cameraUpdate)
    }

    fun searchCurrentLocation() {
        if (setLocationPermission()) {
            homeViewModel.requestLocation()
            binding.buttonCheckTheRealLocation.isVisible = false
        } else {
            HomeAlertDialog().show(parentFragmentManager, DIALOG_TAG)
        }
    }

    private fun setLocationPermission(): Boolean {
        return requireContext().hasPermission((requireActivity() as MainPageActivity).locationPermissions)
    }

    private fun setIconColorAndTextColor(icon: ImageView, text: TextView, color: ColorStateList) {
        icon.imageTintList = color
        text.setTextColor(color)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapOptions = null
        currentMap = null
        currentLocationOverlay = null
    }

    companion object {
        const val DIALOG_TAG = "Permission_denied_dialog"
    }
}
