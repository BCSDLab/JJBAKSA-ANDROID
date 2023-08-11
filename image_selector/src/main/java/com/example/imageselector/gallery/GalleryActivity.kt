package com.example.imageselector.gallery

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageselector.R
import com.example.imageselector.base.BaseActivity
import com.example.imageselector.databinding.ActivityGalleryBinding
import com.example.imageselector.model.Image
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_gallery
    val viewModel: GalleryViewModel by viewModels()
    var maxNum = 10
    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter(this)
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

    private fun getAllPhotos() {
        viewModel.getAllPhotos()
        galleryAdapter.submitList(viewModel.getSelectedImages())
        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 3)
            recyclerView.adapter = galleryAdapter
            recyclerView.itemAnimator = null
        }
    }

    override fun initView() {
        checkPermission()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        getIntentData()
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

    private fun getIntentData() {
        if (intent.hasExtra("limit")) {
            maxNum = intent.getIntExtra("limit", 10)
        }
    }

    override fun subscribe() {
        observeData()
    }

    private fun observeData() {
        viewModel.currentValue.observe(this) {
            if (it >= maxNum) {
                binding.textViewSelectedPictureCount.setTextColor(Color.parseColor("#c4c4c4"))
            } else {
                binding.textViewSelectedPictureCount.setTextColor(Color.parseColor("#ff7f23"))
            }
        }
    }

    override fun initEvent() {
        backToPreviousScreen()
        selectImages()
        sendToImage()
    }

    private fun backToPreviousScreen() {
        binding.imageButtonPreviousArrow.setOnClickListener {
            finish()
        }
    }

    private fun selectImages() {
        galleryAdapter.onClickImageListener = object : GalleryAdapter.OnClickImageListener {
            override fun onImageClick(image: Image, position: Int) {
                if (viewModel.getSelectedImageUri().size < maxNum) {
                    updateImages(position)
                } else {
                    if (viewModel.getSelectedImageUri().contains(image.uri)) {
                        updateImages(position)
                    }
                }
            }
        }
    }

    private fun updateImages(position: Int) {
        viewModel.selectImage(viewModel.getUriArr()[position])
        val updatedImages =
            viewModel.getSelectedImages().map { Image(it.uri, it.index, it.isSelected) }
        galleryAdapter.submitList(updatedImages)
    }

    private fun sendToImage() {
        binding.textViewSendSelectedImage.setOnClickListener {
            sendImageData()
        }
    }

    private fun sendImageData() {
        val intent = Intent()
        val list = ArrayList<String>()
        list.addAll(viewModel.getSelectedImageUri())
        if (list.isNullOrEmpty()) {
            Toast.makeText(this, "이미지를 선택해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            intent.putStringArrayListExtra("images", list)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onDestroy() {
        viewModel.clearData()
        super.onDestroy()
    }
}
