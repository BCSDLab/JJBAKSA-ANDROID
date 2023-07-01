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
    private lateinit var homeAlertDialog: HomeAlertDialog

    private var naverMapOptions: NaverMapOptions? = null
    private var currentNaverMap: NaverMap? = null
    private var currentLocationOverlay: LocationOverlay? = null

    private var isVisibleStoreCategory = false
    private var isStoreCategoryFriend = false
    private var isStoreCategoryBookmark = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNaviHomeBinding?>(inflater, R.layout.fragment_navi_home, container, false).also {
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

        with(binding.buttonCheckLocation) {
            setOnClickListener {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        homeViewModel.locationPermissions[0]
                    ) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(
                        context,
                        homeViewModel.locationPermissions[1]
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    imageTintList = ColorStateList.valueOf(Color.rgb(196, 196, 196))
                    homeViewModel.fusedLocationProvider.requestLastLocation()
                } else {
                    showPermissionInfoDialog()
                }
            }
        }
        setButtonZoomControl()
        setStoreCategorySeeMore()
        setCurrentLocationButton()
        observeData()
    }
    fun onClickFriendStoreCategory() {
        if (!isStoreCategoryBookmark) {
            if (isStoreCategoryFriend) {
                setIconColorAndTextColor(
                    binding.storeCategoryFriendImageView,
                    binding.storeCategoryFriendTextView,
                    HomeColor.Color666666
                )
            } else {
                setIconColorAndTextColor(
                    binding.storeCategoryFriendImageView,
                    binding.storeCategoryFriendTextView,
                    HomeColor.ColorFF7F23
                )
            }
            isStoreCategoryFriend = !isStoreCategoryFriend
        }
    }

    fun onClickBookmarkStoreCategory() {
        if (!isStoreCategoryFriend) {
            if (isStoreCategoryBookmark){
                setIconColorAndTextColor(
                    binding.storeCategoryBookmarkImageView,
                    binding.storeCategoryBookmarkTextView,
                    HomeColor.Color666666
                )
            } else {
                setIconColorAndTextColor(
                    binding.storeCategoryBookmarkImageView,
                    binding.storeCategoryBookmarkTextView,
                    HomeColor.ColorFF7F23
                )
            }
            isStoreCategoryBookmark = !isStoreCategoryBookmark
        }
    }

    private fun setIconColorAndTextColor(icon: ImageView, text: TextView, color: ColorStateList){
        icon.imageTintList = color
        text.setTextColor(color)
    }

    private fun setStoreCategorySeeMore() {
        binding.buttonSeeMore.setOnClickListener {
            isVisibleStoreCategory = !isVisibleStoreCategory
            binding.storeCategoryContainer.isVisible = isVisibleStoreCategory
            if (isVisibleStoreCategory) {
                binding.buttonSeeMore.imageTintList = HomeColor.ColorFF7F23
            } else {
                binding.buttonSeeMore.imageTintList = HomeColor.Color666666
            }
        }
    }

    private fun setCurrentLocationButton() {
        with(binding.buttonCheckTheRealLocation) {
            setOnClickListener {
                this.isVisible = false
            }
        }
    }

    private fun setButtonZoomControl() {
        binding.buttonZoomIn.setOnClickListener {
            val cameraUpdate = CameraUpdate.zoomIn()
            currentNaverMap?.moveCamera(cameraUpdate)
        }
        binding.buttonZoomOut.setOnClickListener {
            val cameraUpdate = CameraUpdate.zoomOut()
            currentNaverMap?.moveCamera(cameraUpdate)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        currentNaverMap = naverMap
        naverMapOptions = NaverMapOptions()
            .mapType(NaverMap.MapType.Basic)
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)

        MapFragment.newInstance(naverMapOptions)

        currentNaverMap?.setContentPadding(0, 0, 0, 0)

        val uiSettings = currentNaverMap?.uiSettings
        uiSettings?.isCompassEnabled = false
        uiSettings?.isZoomControlEnabled = false

        binding.naverMapCompassView.map = currentNaverMap

        naverMap.addOnCameraChangeListener { reason, _ ->
            when (reason) {
                -1, -2 -> {
                    binding.buttonCheckLocation.imageTintList =
                        ColorStateList.valueOf(Color.rgb(255, 127, 35))
                    binding.buttonCheckTheRealLocation.isVisible = true
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
                    if (it.updateCamera) {
                        val cameraUpdate =
                            CameraUpdate.scrollTo(
                                LatLng(
                                    it.currentLatitude!!,
                                    it.currentLongitude!!
                                )
                            )
                        currentNaverMap?.moveCamera(cameraUpdate)
                    }
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

    object HomeColor {
        val ColorFF7F23 = ColorStateList.valueOf(Color.rgb(255, 127, 35))
        val Color666666 = ColorStateList.valueOf(Color.rgb(102, 102, 102))
    }
}
