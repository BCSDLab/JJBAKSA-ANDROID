package com.jjbaksa.jjbaksa.dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseBottomSheetDialogFragment
import com.jjbaksa.jjbaksa.databinding.DialogMypageBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import coil.load
import coil.transform.CircleCropTransformation
import com.example.imageselector.gallery.GalleryActivity
import com.jjbaksa.jjbaksa.util.hasPermission

@AndroidEntryPoint
class MyPageBottomSheetDialog : BaseBottomSheetDialogFragment<DialogMypageBinding>() {
    override val layoutResId: Int
        get() = R.layout.dialog_mypage
    private val viewModel: MyPageViewModel by activityViewModels()

    private val galleryActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val images = it.data!!.getStringArrayListExtra("images")!!
            viewModel.setLoadImage(images[0])
            binding.profileImageView.load(images[0]) {
                transformations(CircleCropTransformation())
            }
        }
    }
    private val requestPermissions = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            val intent = Intent(requireContext(), GalleryActivity::class.java)
            intent.putExtra("limit", 1)
            galleryActivityLauncher.launch(intent)
        } else {
        }
    }

    override fun initView(view: View) {
        binding.vm = viewModel
        viewModel.getUserProfile()
        this.heightPercent = 0.55f
        this.setLayoutMaxHeight(view)
    }

    override fun initEvent() {
        confirmProfile()
        cancelProfile()
        loadProfileImage()
        setTextLength()
        observeData()
    }

    private fun confirmProfile() {
        binding.confirmButton.setOnClickListener {
            if (viewModel.loadImage.value.isNullOrEmpty() || viewModel.textLength.value == "0") {
                // todo:: empty profile image or nickname
            } else {
                viewModel.uploadProfileImgAndNickname(
                    viewModel.loadImage.value.toString(),
                    binding.profileNicknameEditText.text.toString()
                )
            }
        }
    }

    private fun cancelProfile() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun loadProfileImage() {
        binding.addProfileImage.setOnClickListener {
            if (requireContext().hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                val intent = Intent(requireContext(), GalleryActivity::class.java)
                intent.putExtra("limit", 1)
                galleryActivityLauncher.launch(intent)
            } else {
                requestPermissions.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun setTextLength() {
        binding.profileNicknameEditText.addTextChangedListener {
            viewModel.setTextLength(it?.length.toString())
        }
    }

    private fun observeData() {
        viewModel.textLength.observe(viewLifecycleOwner) {
            binding.textLengthCountTextView.text = getString(R.string.text_length, it)
        }
        viewModel.isResult.observe(viewLifecycleOwner) {
            if (it) dismiss()
        }
    }

    override fun subscribe() {
    }

    override fun initData() {
    }
}
