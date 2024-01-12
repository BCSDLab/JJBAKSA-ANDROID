package com.jjbaksa.jjbaksa.ui.mainpage

import android.Manifest
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import com.jjbaksa.jjbaksa.ui.mainpage.home.NaviHomeFragment
import com.jjbaksa.jjbaksa.ui.mainpage.home.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.NaviMyPageFragment
import com.jjbaksa.jjbaksa.ui.mainpage.write.NaviWriteFragment
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.checkPermissionsAndRequest
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
    private val naviHomeFragment: NaviHomeFragment by lazy {
        NaviHomeFragment.newInstance()
    }
    private val naviWriteFragment: NaviWriteFragment by lazy {
        NaviWriteFragment.newInstance()
    }
    private val naviMyPageFragment: NaviMyPageFragment by lazy {
        NaviMyPageFragment.newInstance()
    }
    override fun initView() {
        binding.lifecycleOwner = this
        viewModel.getMyInfo()
        checkPermission()
        showHomeFragment()
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

    override fun initEvent() {
        with(binding) {
            bottomHome.setOnClickListener {
                showFragment(naviHomeFragment, NaviHomeFragment.TAG)
                binding.navigationView.visibility = View.VISIBLE
                binding.ivBottomHome.isSelected = true
                binding.tvBottomHome.isSelected = true
            }
            bottomWrite.setOnClickListener {
                showFragment(naviWriteFragment, NaviWriteFragment.TAG)
                binding.navigationView.visibility = View.GONE
                binding.ivBottomWrite.isSelected = true
                binding.tvBottomWrite.isSelected = true
            }
            bottomMypage.setOnClickListener {
                showFragment(naviMyPageFragment, NaviMyPageFragment.TAG)
                binding.navigationView.visibility = View.VISIBLE
                binding.ivBottomMypage.isSelected = true
                binding.tvBottomMypage.isSelected = true
            }
        }
    }
    fun showHomeFragment() {
        binding.ivBottomHome.isSelected = true
        binding.tvBottomHome.isSelected = true
        binding.bottomHome.performClick()
    }
    private fun showFragment(fragment: Fragment, tag: String) {
        with(binding) {
            ivBottomHome.isSelected = false
            ivBottomWrite.isSelected = false
            ivBottomMypage.isSelected = false
            tvBottomHome.isSelected = false
            tvBottomWrite.isSelected = false
            tvBottomMypage.isSelected = false
        }
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }
        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    companion object {
        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}
