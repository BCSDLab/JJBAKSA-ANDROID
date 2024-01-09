package com.jjbaksa.jjbaksa.dialog

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
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

@AndroidEntryPoint
class MyPageBottomSheetDialog : BaseBottomSheetDialogFragment<DialogMypageBinding>() {
    override val layoutResId: Int
        get() = R.layout.dialog_mypage
    private val viewModel: MyPageViewModel by activityViewModels()

    private val galleryActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val images = it.data!!.getStringArrayListExtra("images")!!
                viewModel.setLoadImage(images[0])
                binding.profileImageView.load(images[0]) {
                    transformations(CircleCropTransformation())
                }
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
    }

    private fun confirmProfile() {
        binding.confirmButton.setOnClickListener {
            if (viewModel.textLength.value == "0") {
                Toast.makeText(requireContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!isNicknameAvailable(binding.profileNicknameEditText.text.toString())) {
                Toast.makeText(requireContext(), "닉네임에 특수문자는 포함할 수 없어요.", Toast.LENGTH_SHORT)
                    .show()
            } else if (viewModel.loadImage.value.isNullOrEmpty() && viewModel.textLength.value != "0") {
                viewModel.setNewNickname(binding.profileNicknameEditText.text.toString())
            } else if (viewModel.loadImage.value != null && viewModel.textLength.value != "0") {
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
            val intent = Intent(requireContext(), GalleryActivity::class.java)
            intent.putExtra("limit", 1)
            galleryActivityLauncher.launch(intent)
        }
    }

    private fun setTextLength() {
        binding.profileNicknameEditText.addTextChangedListener {
            viewModel.setTextLength(it?.length.toString())
        }
    }

    override fun subscribe() {
        observeData()
    }

    private fun observeData() {
        viewModel.textLength.observe(viewLifecycleOwner) {
            binding.textLengthCountTextView.text = getString(R.string.text_length, it)
        }
        viewModel.isResult.observe(viewLifecycleOwner) {
            if (it) dismiss()
        }
    }

    override fun initData() {
    }

    private fun isNicknameAvailable(temp: String): Boolean {
        val specialCharRegex = Regex("[\\p{P}\\s]")
        return specialCharRegex.find(temp) == null
    }
}
