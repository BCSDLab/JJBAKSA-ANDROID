package com.example.imageselector.gallery

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageselector.R
import com.example.imageselector.base.BaseActivity
import com.example.imageselector.databinding.ActivityGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_gallery
    val viewModel: GalleryViewModel by viewModels()
    var maxNum = 10
    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter(this, viewModel.getSelectedImageList(), viewModel.getUriArr(), maxNum) {
            viewModel.selectImage(viewModel.getUriArr()[it])
            galleryAdapter.notifyDataSetChanged()
        }
    }
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> getAllPhotos()
                else -> {
                    Toast.makeText(applicationContext, "권한 허용 필요", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    private val requestMultiplePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            results.forEach {
                when (it.value) {
                    true -> getAllPhotos()
                    else -> {
                        Toast.makeText(applicationContext, "권한 허용 필요", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

    private fun sendImageData() {
        val intent = Intent()
        intent.putStringArrayListExtra("images", viewModel.getSelectedImageUri())
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun getAllPhotos() {
        viewModel.getAllPhotos()
        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 3)
            recyclerView.adapter = galleryAdapter
        }
    }

    private fun checkPermission() {
        val permissionList = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            requestMultiplePermission.launch(permissionList)
        }
    }

    override fun initView() {
        checkPermission()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        getIntentData()
    }

    override fun subscribe() {
        viewModel.currentValue.observe(this) {
            if (it >= maxNum) {
                binding.textViewSelectedPictureCount.setTextColor(Color.parseColor("#c4c4c4"))
            } else {
                binding.textViewSelectedPictureCount.setTextColor(Color.parseColor("#ff7f23"))
            }
        }
    }

    override fun initEvent() {
        with(binding) {
            textViewSendSelectedImage.setOnClickListener {
                sendImageData()
            }
            imageButtonPreviousArrow.setOnClickListener {
                finish()
            }
        }
    }

    fun getIntentData() {
        if (intent.hasExtra("limit")) {
            maxNum = intent.getIntExtra("limit", 10)
        }
    }
}
