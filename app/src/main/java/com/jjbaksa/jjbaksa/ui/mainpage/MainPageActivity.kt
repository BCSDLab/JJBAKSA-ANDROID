package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.ui.mainpage.sub.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    private val naviFragmentHome by lazy { NaviHomeFragment.newInstance() }
    private val naviFragmentWrite by lazy { NaviWriteFragment.newInstance() }
    private val naviFragmentMyPage by lazy { NaviMyPageFragment.newInstance() }
    override val layoutId: Int
        get() = R.layout.activity_main_page

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAlertDialog: HomeAlertDialog
    private lateinit var fusedLocationProvider: FusedLocationProvider

    private val requestLocationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        when {
            isGranted.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                fusedLocationProvider.requestLastLocation()
                fusedLocationProvider.startLocationUpdates()
            }
            isGranted.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                fusedLocationProvider.requestLastLocation()
                fusedLocationProvider.startLocationUpdates()
            }
            else -> {
                if (isShouldShowRequestPermissionRationale(locationPermissions[0]) && isShouldShowRequestPermissionRationale(
                        locationPermissions[1]
                    )
                ) {
                    showPermissionInfoDialog()
                }
            }
        }
    }

    override fun initView() {
        binding.lifecycleOwner = this
        fusedLocationProvider = FusedLocationProvider(this, homeViewModel)
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        initNavigationBar()
        checkLocationPermissions()
    }

    private fun initNavigationBar() {
        binding.navigationView.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homeItem -> {
                        changeFragment(naviFragmentHome)
                    }
                    R.id.writeItem -> {
                        changeFragment(naviFragmentWrite)
                    }
                    R.id.myPageItem -> {
                        changeFragment(naviFragmentMyPage)
                    }
                }
                true
            }
            selectedItemId = R.id.homeItem
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    private fun checkLocationPermissions() {
        if (isPermissionGranted(locationPermissions[0]) && isPermissionGranted(locationPermissions[1])) {
            fusedLocationProvider.requestLastLocation()
            fusedLocationProvider.startLocationUpdates()
        } else {
            requestLocationPermissions.launch(locationPermissions)
        }
    }

    private fun showPermissionInfoDialog() {
        homeAlertDialog = HomeAlertDialog()
        homeAlertDialog.show(supportFragmentManager, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationProvider.stopLocationUpdates()
    }
}

