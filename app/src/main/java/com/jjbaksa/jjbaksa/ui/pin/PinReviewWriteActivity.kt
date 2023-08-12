package com.jjbaksa.jjbaksa.ui.pin

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.imageselector.gallery.GalleryActivity
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPinReviewWriteBinding
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinReviewImageAdapter
import com.jjbaksa.jjbaksa.ui.pin.viewmodel.PinReviewWriteViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinReviewWriteActivity : BaseActivity<ActivityPinReviewWriteBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_pin_review_write
    private val viewModel: PinReviewWriteViewModel by viewModels()

    private val pinReviewImageAdapter by lazy {
        PinReviewImageAdapter(::onDelete)
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val images = it.data?.getStringArrayListExtra("images")
                images?.let { imageList ->
                    viewModel.setImageList(imageList)
                    pinReviewImageAdapter.submitList(viewModel.imageList.value)
                }
            }
        }

    override fun initView() {
        intent.getStringExtra("name")?.let {
            binding.titleTextView.text = it
        }
        intent.getStringExtra("place_id")?.let {
            viewModel.placeId.value = it
        }
        binding.recyclerView.apply {
            this.adapter = pinReviewImageAdapter
            this.itemAnimator = null
        }

    }

    override fun subscribe() {
        viewModel.isReview.observe(this) {
            if (it) {
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "리뷰 작성 오류", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.saveButton.setOnClickListener {
            binding.saveButton.isSelected = !binding.saveButton.isSelected
            if (binding.saveButton.isSelected) {
                binding.saveButton.setTextColor(getColor(R.color.color_ffffff))
            } else {
                binding.saveButton.setTextColor(getColor(R.color.color_ff7f23))
            }

            if (binding.contentEditText.text.isNotEmpty() &&
                    binding.ratingBar.rating != 0.0f &&
                    !viewModel.imageList.value.isNullOrEmpty()
            ) {
                viewModel.setReview(
                    viewModel.placeId.value.toString(),
                    binding.contentEditText.text.toString(),
                    binding.ratingBar.rating.toInt(),
                    viewModel.imageList.value?.toList().orEmpty()
                )
            } else {
                Toast.makeText(this, "리뷰 & 별점 & 이미지를 추가해주세요.", Toast.LENGTH_SHORT).show()
                KeyboardProvider(this).hideKeyboard(binding.contentEditText)
            }
        }
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            binding.starCountTextView.text = rating.toString()
        }
        binding.addImageButton.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java).apply {
                putExtra("limit", 10)
            }
            galleryResult.launch(intent)
        }
    }

    private fun onDelete(position: Int) {
        viewModel.removeImageList(position)
        pinReviewImageAdapter.notifyItemRemoved(position)
        pinReviewImageAdapter.notifyItemRangeChanged(position, viewModel.imageList.value?.size!!)
    }
}
