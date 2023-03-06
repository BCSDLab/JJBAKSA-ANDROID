package com.jjbaksa.jjbaksa.ui.gallery

import android.Manifest
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_gallery
    val viewModel: GalleryViewModel by viewModels()
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when (isGranted) {
                true -> getAllPhotos()
                else -> {
                    when (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        true -> permissionDialog(true)
                        else -> permissionDialog(false)
                    }
                }
            }
        }

    private fun sendImageData() {
        val intent = Intent()
        intent.putStringArrayListExtra("images",viewModel.getSelectedImageUri())
        setResult(RESULT_OK,intent)
        finish()
    }

    private fun getAllPhotos() {
        viewModel.getAllPhotos()

        val uriArr = viewModel.getUriArr()
        val selectedImage = viewModel.getSelectedImageList()
        val galleryAdapter = GalleryAdapter(this,selectedImage,uriArr)
        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 3)
            recyclerView.adapter = galleryAdapter
        }

        galleryAdapter.setOnClickListener {
            viewModel.selectImage(uriArr[it])
            galleryAdapter.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun permissionDialog(isDeniedOnce: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("권한이 필요합니다.")
            .setMessage("ok 버튼을 눌러주세요.")
            .setPositiveButton("ok") { _, _ ->
                if (isDeniedOnce) {
                    checkPermission()
                } else {
                }
            }
            .setNegativeButton("cancel") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
        builder.show()
    }

    private fun checkPermission() {
        requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun initView() {
        checkPermission()
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            textViewSendSelectedImage.setOnClickListener {
                sendImageData()
            }
            imageButtonPreviousArrow.setOnClickListener{
                finish()
            }
        }
    }
}
