package com.jjbaksa.jjbaksa.dialog

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

@AndroidEntryPoint
class MyPageDialog : BaseBottomSheetDialogFragment<DialogMypageBinding>() {
    override val layoutResId: Int
        get() = R.layout.dialog_mypage
    private val viewModel: MyPageViewModel by activityViewModels()
    private val loadImageUri = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) {
            return@registerForActivityResult
        } else {
            viewModel.setProfileImage(uri.toString())
        }
    }


    override fun initView(view: View) {
        binding.vm = viewModel
        viewModel.getUserProfile()
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
        }
    }

    private fun cancelProfile() {
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun loadProfileImage() {
        binding.addProfileImage.setOnClickListener {
            loadImageUri.launch("image/*")
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
        viewModel.profileImage.observe(viewLifecycleOwner) {
            binding.profileImageView.load(it) {
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun subscribe() {
    }

    override fun initData() {
    }
}
