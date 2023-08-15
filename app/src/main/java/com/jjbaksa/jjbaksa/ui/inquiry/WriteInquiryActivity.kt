package com.jjbaksa.jjbaksa.ui.inquiry

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.imageselector.gallery.GalleryActivity
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityWriteInquiryBinding
import com.jjbaksa.jjbaksa.ui.inquiry.adapter.InquiryPhotoAdapter
import com.jjbaksa.jjbaksa.ui.inquiry.viewmodel.WriteInquiryViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteInquiryActivity : BaseActivity<ActivityWriteInquiryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_write_inquiry
    private val viewModel: WriteInquiryViewModel by viewModels()
    private val inquiryPhotoAdapter: InquiryPhotoAdapter by lazy {
        InquiryPhotoAdapter(this::deletePhoto)
    }

    private val startGalleryActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val images = it.data!!.getStringArrayListExtra("images")!!
                viewModel.isExtraPhoto.value = !images.isNullOrEmpty()
                viewModel.setPhotoList(images)
                inquiryPhotoAdapter.submitList(viewModel.photoList.value)
            }
        }

    override fun initView() {
        binding.lifecycleOwner = this
        binding.view = this
        binding.vm = viewModel
        KeyboardProvider(this).inputKeyboardResize(window, binding.root)
        binding.inquiryPhotoRecyclerView.apply {
            this.adapter = inquiryPhotoAdapter
            this.itemAnimator = null
        }
    }

    override fun subscribe() {
        observeData()
    }

    private fun observeData() {
        viewModel.isCheckableButton.observe(this) {
            binding.registerButton.apply {
                isSelected = it
                isEnabled = it
            }
        }
        viewModel.isVisibleAddPhoto.observe(this) {
            binding.addPhotoImageView.isVisible = it
        }
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.contentEditText.addTextChangedListener {
            viewModel.inquiryContentLength.value = it?.length
            viewModel.isCheckableButton.value =
                binding.titleEditText.text?.length != 0 && it?.length != 0
        }
        binding.titleEditText.addTextChangedListener {
            viewModel.isCheckableButton.value =
                viewModel.inquiryContentLength.value != 0 && it?.length != 0
        }
        binding.registerButton.setOnClickListener {
        }
    }

    fun goToGallery() {
        val intent = Intent(this, GalleryActivity::class.java).apply {
            putExtra("limit", 3)
        }
        startGalleryActivity.launch(intent)
    }

    private fun deletePhoto(position: Int) {
        viewModel.removePhotoListItem(position)
        inquiryPhotoAdapter.notifyItemRemoved(position)
        inquiryPhotoAdapter.notifyItemRangeChanged(position, viewModel.photoList.value?.size!!)
    }
}
