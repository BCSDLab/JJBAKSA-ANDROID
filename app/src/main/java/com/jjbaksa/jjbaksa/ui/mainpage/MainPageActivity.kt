package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.test.espresso.ViewInteractionModule_ProvideRootViewFactory.create
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import com.jjbaksa.jjbaksa.ui.login.LoginViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeAlertDialog
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    private val naviFragmentHome by lazy { NaviHomeFragment.newInstance() }
    private val naviFragmentWrite by lazy { NaviWriteFragment.newInstance() }
    private val naviFragmentMyPage by lazy { NaviMyPageFragment.newInstance() }
    override val layoutId: Int
        get() = R.layout.activity_main_page

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAlertDialog: HomeAlertDialog

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perm ->
        when {
            perm.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                requestLocation()
            }
            perm.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                requestLocation()
            }
            else -> {}
        }
    }

    override fun initView() {
        binding.lifecycleOwner = this
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        initNavigationBar()
        checkPermission()
    }

    override fun onStart() {
        super.onStart()
        if (homeViewModel.userLocation.value == null){
            for (perm in homeViewModel.locationPermission){
                if (ContextCompat.checkSelfPermission(this, perm) == 0){
                    homeViewModel.loadLocation(LocationServices.getFusedLocationProviderClient(this))
                }
            }
        }

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

    private fun requestLocation() {
        ActivityCompat.requestPermissions(this, homeViewModel.locationPermission, PERMISSION_REQUEST_CODE)
    }

    private fun checkPermission() {
        for (perm in homeViewModel.locationPermission){
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    perm
                ) == PackageManager.PERMISSION_GRANTED -> {
                    locationPermissionRequest.launch(homeViewModel.locationPermission)
                }
                shouldShowRequestPermissionRationale(perm) -> {
                    showPermissionInfoDialog()
                    return
                }
                else -> {
                    locationPermissionRequest.launch(homeViewModel.locationPermission)
                }
            }
        }
    }

    private fun showPermissionInfoDialog() {
        homeAlertDialog = HomeAlertDialog()
        homeAlertDialog.show(supportFragmentManager, "")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    homeViewModel.loadLocation(LocationServices.getFusedLocationProviderClient(this))
                } else {
                    // when Permission Denied
                }
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }
}

