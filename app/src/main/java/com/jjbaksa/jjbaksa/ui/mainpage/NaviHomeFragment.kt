package com.jjbaksa.jjbaksa.ui.mainpage

import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jjbaksa.domain.model.mainpage.UserLocation
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.ui.mainpage.sub.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.OverlayImage

class NaviHomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentNaviHomeBinding

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var homeAlertDialog: HomeAlertDialog

    private var naverMapOptions: NaverMapOptions? = null
    private var currentNaverMap: NaverMap? = null
    private var currentLocationOverlay: LocationOverlay? = null

    var changedButtonList = mutableListOf<Boolean>(false, false, false)

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
                    homeViewModel.fusedLocationProvider.requestLastLocation()
                } else {
                    showPermissionInfoDialog()
                }
            }
        }

        with(binding.buttonNearbyRestaurant) {
            setOnClickListener {
                if (changedButtonList[0]) onChangeButton(0, this) else onChangeButton(0, this)
            }
        }
        with(binding.buttonFriendRestaurant) {
            setOnClickListener {
                if (changedButtonList[1]) onChangeButton(1, this) else onChangeButton(1, this)
            }
        }
        with(binding.buttonBookmarkRestaurant) {
            setOnClickListener {
                if (changedButtonList[2]) onChangeButton(2, this) else onChangeButton(2, this)
            }
        }
    }

    private fun onChangeButton(pos: Int, button: Button) {
        if (changedButtonList[pos]) {
            with(button) {
                changedButtonList[pos] = false
                setBackgroundResource(R.drawable.shape_rect_fbfbfa_stroke_c4c4c4_radius_37)
                compoundDrawableTintList = Color666666
                setTextColor(resources.getColor(R.color.color_c4c4c4))
            }
        } else {
            with(button) {
                changedButtonList[pos] = true
                setBackgroundResource(R.drawable.shape_rect_fbfbfa_stroke_ff7f23_radius_37)
                compoundDrawableTintList = ColorFF7F23
                setTextColor(resources.getColor(R.color.color_ff7f23))
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        currentNaverMap = naverMap
        naverMapOptions = NaverMapOptions()
            .mapType(NaverMap.MapType.Basic)
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)

        MapFragment.newInstance(naverMapOptions)

        val uiSettings = currentNaverMap?.uiSettings
        uiSettings?.isCompassEnabled = false

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
                    if (it.updateCamera){
                        val cameraUpdate =
                            CameraUpdate.scrollTo(LatLng(it.currentLatitude!!, it.currentLongitude!!))
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

        val ColorFF7F23 = ColorStateList.valueOf(Color.rgb(255, 127, 35))
        val Color666666 = ColorStateList.valueOf(Color.rgb(102, 102, 102))
    }
}
