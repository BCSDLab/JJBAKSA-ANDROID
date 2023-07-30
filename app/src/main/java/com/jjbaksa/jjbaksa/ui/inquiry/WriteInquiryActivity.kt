package com.jjbaksa.jjbaksa.ui.inquiry

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.imageselector.gallery.GalleryActivity
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityWriteInquiryBinding
import com.jjbaksa.jjbaksa.ui.inquiry.viewmodel.WriteInquiryViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteInquiryActivity : BaseActivity<ActivityWriteInquiryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_write_inquiry
    private val viewModel: WriteInquiryViewModel by viewModels()

    private val startGalleryActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        
    }

    override fun initView() {
        binding.lifecycleOwner = this
        binding.view = this
        binding.vm = viewModel
        KeyboardProvider(this).inputKeyboardResize(window, binding.root)
    }

    override fun subscribe() {
        observeData()
    }

    private fun observeData() {
        viewModel.isCheckableButton.observe(this) {
            binding.registerButton.isSelected = it
        }
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.contentEditText.addTextChangedListener {
            viewModel.inquiryContentLength.value = it?.length
        }
        binding.titleEditText.addTextChangedListener {
            viewModel.isCheckableButton.value =
                viewModel.inquiryContentLength.value != 0 && it?.length != 0
        }
        binding.registerButton.setOnClickListener { }
    }

    fun goToGallery() {
        val intent = Intent(this, GalleryActivity::class.java).apply {
            putExtra("limit", 3)
        }
        startGalleryActivity.launch(intent)
    }
}