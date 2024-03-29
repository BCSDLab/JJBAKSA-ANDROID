package com.example.imageselector.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutId: Int
    private lateinit var _binding: T
    val binding: T
        get() = _binding

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        initState()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    abstract fun initView()
    abstract fun subscribe()
    abstract fun initEvent()
    open fun initState() {
        initView()
        initEvent()
        subscribe()
    }

    fun isPermissionGranted(perm: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED
    }

    fun isShouldShowRequestPermissionRationale(perm: String): Boolean {
        return shouldShowRequestPermissionRationale(perm)
    }
}
