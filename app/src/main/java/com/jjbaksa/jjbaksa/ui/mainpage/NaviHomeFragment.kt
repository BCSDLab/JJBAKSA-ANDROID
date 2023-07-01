package com.jjbaksa.jjbaksa.ui.mainpage

import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.ui.mainpage.sub.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.util.ColorObject
import com.jjbaksa.jjbaksa.util.WindowProvider
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.OverlayImage

class NaviHomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentNaviHomeBinding

    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var cameraUpdate: CameraUpdate
    private var mapOptions: NaverMapOptions? = null
    private var currentMap: NaverMap? = null
    private var currentLocationOverlay: LocationOverlay? = null

    private var isVisibleStoreCategory = false
    private var isStoreCategoryFriend = false
    private var isStoreCategoryBookmark = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNaviHomeBinding.inflate(layoutInflater).also {
            it.view = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowProvider(requireActivity()).clearStatusBar(requireActivity().getColor(R.color.color_00000000))

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        observeData()
    }

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
        if (setLocationPermission()){
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
        if (setLocationPermission()){
            homeViewModel.requestLocation()
            binding.buttonCheckTheRealLocation.isVisible = false
        } else {
            HomeAlertDialog().show(parentFragmentManager, DIALOG_TAG)
        }
    }

    private fun setLocationPermission(): Boolean {
        return checkPermission(homeViewModel.locationPermissions[0]) || checkPermission(homeViewModel.locationPermissions[1])
    }

    private fun checkPermission(perm: String): Boolean =
        ActivityCompat.checkSelfPermission(requireContext(), perm) == PackageManager.PERMISSION_GRANTED

    private fun setIconColorAndTextColor(icon: ImageView, text: TextView, color: ColorStateList) {
        icon.imageTintList = color
        text.setTextColor(color)
    }

    private fun observeData() {
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
