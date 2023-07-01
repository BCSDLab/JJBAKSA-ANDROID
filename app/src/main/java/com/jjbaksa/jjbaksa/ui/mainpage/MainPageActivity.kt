package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.ui.mainpage.sub.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main_page

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAlertDialog: HomeAlertDialog

    private val requestLocationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        when {
            isGranted.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                homeViewModel.requestLocation()
            }
            isGranted.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                homeViewModel.requestLocation()
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
        homeViewModel.fusedLocationProvider = FusedLocationProvider(this, homeViewModel)
    }

    override fun subscribe() {}

    override fun initEvent() {
        initNavigation()
        checkLocationPermissions()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(navController)
    }

    private fun checkLocationPermissions() {
        if (isPermissionGranted(locationPermissions[0]) && isPermissionGranted(locationPermissions[1])) {
            homeViewModel.requestLocation()
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
        homeViewModel.fusedLocationProvider.stopLocationUpdates()
    }
}
