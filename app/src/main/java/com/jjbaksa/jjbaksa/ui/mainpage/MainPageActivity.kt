package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import com.jjbaksa.jjbaksa.ui.mainpage.sub.FusedLocationProvider
import com.jjbaksa.jjbaksa.dialog.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.hasPermission
import com.jjbaksa.jjbaksa.util.setStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main_page

    private val viewModel: HomeViewModel by viewModels()
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val requestLocationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        when {
            isGranted.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                viewModel.requestLocation()
            }

            isGranted.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                viewModel.requestLocation()
            }

            else -> {
                if (!shouldShowRequestPermissionRationale(locationPermissions[0]) &&
                    !shouldShowRequestPermissionRationale(locationPermissions[1])
                ) {
                    HomeAlertDialog().show(supportFragmentManager, DIALOG_TAG)
                }
            }
        }
    }

    override fun initView() {
        viewModel.fusedLocationProvider = FusedLocationProvider(this, viewModel)
        this.setStatusBarTransparent()
        viewModel.getMyInfo()
        binding.lifecycleOwner = this
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
        if (hasPermission(locationPermissions)) {
            viewModel.requestLocation()
        } else {
            requestLocationPermissions.launch(locationPermissions)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.fusedLocationProvider.stopLocationUpdates()
    }

    companion object {
        const val DIALOG_TAG = "Permission_denied_dialog"
    }
}
