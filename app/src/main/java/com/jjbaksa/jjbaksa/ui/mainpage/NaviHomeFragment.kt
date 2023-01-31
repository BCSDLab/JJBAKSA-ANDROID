package com.jjbaksa.jjbaksa.ui.mainpage

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentNaviHomeBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class NaviHomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentNaviHomeBinding

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

        with(binding.buttonCheckLocation){
            setOnClickListener {
                imageTintList = ColorStateList.valueOf(Color.rgb(196,196,196))
            }
        }

        with(binding.buttonNearbyRestaurant){
            setOnClickListener {
            }
        }
        with(binding.buttonFriendRestaurant){
            setOnClickListener {
            }
        }
        with(binding.buttonBookmarkRestaurant){
            setOnClickListener {
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.566, 126.978),  10.0))  // 카메라 위치 (위도,경도,줌)
            .mapType(NaverMap.MapType.Basic)    //지도 유형
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)  //빌딩 표시

        MapFragment.newInstance(options)

        val marker = Marker()
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
