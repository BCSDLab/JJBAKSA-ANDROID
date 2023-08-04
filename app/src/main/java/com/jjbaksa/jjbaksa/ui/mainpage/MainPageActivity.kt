package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import com.jjbaksa.jjbaksa.ui.mainpage.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.util.checkPermissionsAndRequest
import com.jjbaksa.jjbaksa.util.hasPermission
import com.jjbaksa.jjbaksa.util.setStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main_page

    private val viewModel: HomeViewModel by viewModels()

    private val locationPermissionsResult = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        when {
            isGranted.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // TODO : ACCESS_COARSE_LOCATION 위치 권한 허용 시
            }

            isGranted.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // TODO : ACCESS_FINE_LOCATION 위치 권한 허용 시
            }

            else -> {
                // TODO : ACCESS_COARSE_LOCATION && ACCESS_FINE_LOCATION 위치 권한 거부
            }
        }
    }

    override fun initView() {
        this.setStatusBarTransparent()
        binding.lifecycleOwner = this
        viewModel.getMyInfo()
        initNavigation()
        checkLocationPermissions()
    }

    override fun subscribe() {}

    override fun initEvent() {}

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(navController)
    }

    private fun checkLocationPermissions() {
        if (checkPermissionsAndRequest(locationPermissions, locationPermissionsResult)) {
            // TODO : 위치 권한 허용 시 위치 정보 불러오기
        }
    }

    companion object {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}
