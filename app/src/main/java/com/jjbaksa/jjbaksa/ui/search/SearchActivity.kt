package com.jjbaksa.jjbaksa.ui.search

import android.Manifest
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySearchBinding
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchMainViewModel
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchViewModel
import com.jjbaksa.jjbaksa.util.OpenSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_search

    private val searchViewModel: SearchViewModel by viewModels()
    private val searchMainViewModel: SearchMainViewModel by viewModels()

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            for (result in results) {
                when (result.value) {
                    true -> {
                        getGPSLocation()
                        break
                    }
                    else -> {
                        when (
                            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
                                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                        ) {
                            true -> permissionDialog(true)
                            else -> permissionDialog(false)
                        }
                        break
                    }
                }
            }
        }

    private val openSettings =
        registerForActivityResult(OpenSettings()) {
            checkPermission()
        }

    private val openLocationSettings =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            getGPSLocation()
        }

    override fun initView() {
        binding.lifecycleOwner = this
        binding.vm = searchViewModel
        binding.toolbarSearchTitle.setNavigationIcon(R.drawable.ic_toolbar_back)
        setSupportActionBar(binding.toolbarSearchTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkPermission()
    }

    override fun subscribe() {
        searchViewModel.title.observe(this) {
            supportActionBar?.title = it
        }

        searchViewModel.isLoading.observe(this) {
            if (it) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                window.clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
        }
    }

    override fun initEvent() {
    }

    private fun checkPermission() {
        val permissionList = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        requestPermission.launch(permissionList)
    }

    private fun permissionDialog(isDeniedOnce: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.search_ask_locate_permission_dialog_title))
            .setMessage(getString(R.string.search_ask_locate_permission_dialog_message))
            .setPositiveButton(getString(R.string.search_ask_locate_permission_dialog_ok)) { dialog, _ ->
                when (isDeniedOnce) {
                    true -> checkPermission()
                    false -> {
                        openSettings.launch(null)
                        Toast.makeText(this, getString(R.string.search_ask_locate_permission_setting), Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.search_ask_locate_permission_dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, getString(R.string.search_need_permission_toast_message), Toast.LENGTH_SHORT).show()
                finish()
            }
            .setCancelable(false)
        builder.show()
    }

    private fun getGPSLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationDialog()
        } else {
            searchViewModel.getLocation()
        }
    }

    private fun locationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.search_ask_location_needed_dialog_title))
            .setMessage(getString(R.string.search_ask_location_needed_dialog_message))
            .setPositiveButton(getString(R.string.search_ask_location_needed_dialog_ok)) { dialog, _ ->
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).also { openLocationSettings.launch(it) }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.search_ask_location_needed_dialog_cancel)) { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, getString(R.string.search_need_location_toast_message), Toast.LENGTH_SHORT).show()
                finish()
            }
            .setCancelable(false)
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (searchViewModel.isSearching.value == true) {
                    searchViewModel.setSearching(false)
                } else {
                    this.onBackPressed()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
