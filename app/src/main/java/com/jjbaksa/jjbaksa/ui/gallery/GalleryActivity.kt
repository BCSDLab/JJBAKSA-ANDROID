package com.jjbaksa.jjbaksa.ui.gallery

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_gallery
    private var imageUriList = arrayListOf<Image>()

    private val requestGalleryLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            if (it.data!!.clipData != null) {
                val count = it.data!!.clipData!!.itemCount
                for (index in 0 until count) {
                    val imageUri = it.data!!.clipData!!.getItemAt(index).uri
                    imageUriList.add(Image(imageUri))
                }
            } else {
                val imageUri = it.data!!.data
                imageUriList.add(Image(imageUri!!))
            }
            sendImageData(imageUriList)
        }
    }

    fun sendImageData(imageUriList: ArrayList<Image>) {
        val intent = Intent()
        intent.putParcelableArrayListExtra("imageUriList",imageUriList)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun initView() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        requestGalleryLauncher.launch(intent)
    }

    override fun subscribe() {

    }

    override fun initEvent() {

    }
}
