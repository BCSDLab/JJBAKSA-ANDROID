package com.jjbaksa.jjbaksa.ui.gallery

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
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
        intent.putStringArrayListExtra("images",viewModel.selectedImageUri)
        setResult(RESULT_OK,intent)
        finish()
    }

    private fun getAllPhotos() {
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")
        val uriArr = ArrayList<String>()
        val imageList = ArrayList<Image>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriArr.add(uri)
            }
            cursor.close()
        }
        for (uri in uriArr) {
            viewModel.selectedImage.add(Image(MutableLiveData(uri),MutableLiveData(0),false))
            imageList.add(Image(MutableLiveData(uri),MutableLiveData(0),false))
        }

        val galleryAdapter = GalleryAdapter(this, viewModel.selectedImage,uriArr,viewModel,this)
        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 3)
            recyclerView.adapter = galleryAdapter
        }

        galleryAdapter.setOnClickListener {
            Log.e("gallery","${it}")
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
//                    openSettings.launch(null)
                    Log.e("gallery", "hi")
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
