package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.app.AlertDialog
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.checkPermissionsAndRequest
import com.jjbaksa.jjbaksa.util.hasPermission
import com.jjbaksa.jjbaksa.util.setStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main_page

    private val viewModel: HomeViewModel by viewModels()

    private val locationResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->
            when {
                isGranted.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getLastLocation()
                }

                isGranted.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    getLastLocation()
                }
            }
        }

    override fun initView() {
        this.setStatusBarTransparent()
        binding.lifecycleOwner = this
        viewModel.getMyInfo()
        checkPermission()
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(navController)
    }

    private fun checkPermission() {
        checkPermissionsAndRequest(locationPermissions, locationResult)
    }

    private fun getLastLocation() {
        FusedLocationUtil(this).getLastLocation()?.addOnSuccessListener {
            if (it == null) return@addOnSuccessListener
            viewModel.setLastLocation(it.latitude, it.longitude)
        }
    }

    override fun subscribe() {}

    override fun initEvent() {}

    companion object {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}
