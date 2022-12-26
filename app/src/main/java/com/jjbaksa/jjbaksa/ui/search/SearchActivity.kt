package com.jjbaksa.jjbaksa.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySearchBinding
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchMainViewModel
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchViewModel
import com.jjbaksa.jjbaksa.util.OpenSettings
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_search

    private val searchViewModel: SearchViewModel by viewModels()
    private val searchMainViewModel: SearchMainViewModel by viewModels()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            for (result in results) {
                when (result.value) {
                    true -> {
                        getGPSLocation()
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

    override fun initView() {
        binding.lifecycleOwner = this
        binding.toolbarSearchTitle.setNavigationIcon(R.drawable.ic_toolbar_back)
        setSupportActionBar(binding.toolbarSearchTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkPermission()
    }

    override fun subscribe() {
        searchViewModel.title.observe(this) {
            supportActionBar?.title = it
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

    @SuppressLint("MissingPermission")
    fun getGPSLocation() {
        val priority = Priority.PRIORITY_HIGH_ACCURACY
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient.getCurrentLocation(priority, null)
            .addOnSuccessListener { location ->
                searchViewModel.setNewLocation(location)
            }
            .addOnFailureListener { exception ->
                finish()
                Timber.d("location failed $exception")
            }
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
