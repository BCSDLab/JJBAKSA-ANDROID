package com.jjbaksa.jjbaksa.ui.pin

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPinBinding
import com.jjbaksa.jjbaksa.ui.pin.adapter.ImageFrameAdapter
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinAdapter
import com.jjbaksa.jjbaksa.ui.pin.viewmodel.PinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinActivity : BaseActivity<ActivityPinBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_pin
    private val viewModel: PinViewModel by viewModels()
    private lateinit var imageFrameAdapter: ImageFrameAdapter

    private val reviewWriteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            if (viewModel.myReviewUpdateCursor.value == MyReviewCursor.LATEST) {
                viewModel.getMyReview(
                    placeId = "ChIJBahxzkWjfDUR7iD24mIMTHU",
                    // placeId = viewModel.placeId.value.toString()
                    size = 10
                )
            } else if (viewModel.myReviewUpdateCursor.value == MyReviewCursor.STAR) {
                viewModel.getMyReview(
                    placeId = "ChIJBahxzkWjfDUR7iD24mIMTHU",
                    // placeId = viewModel.placeId.value.toString()
                    sort = "rate",
                    size = 10
                )
            }
        }
    }

    override fun initView() {
        intent.getStringExtra("place_id")?.let {
            viewModel.placeId.value = it
            viewModel.getShopDetail(it)
        }
        initViewPagerWithTabLayout()
        initShopImageViewPagerWithTabLayout()
    }

    private fun initShopImageViewPagerWithTabLayout() {
        imageFrameAdapter = ImageFrameAdapter()
        binding.shopImagesViewPager.adapter = imageFrameAdapter

        TabLayoutMediator(
            binding.shopImagesTabLayout,
            binding.shopImagesViewPager
        ) { tab, position ->
            binding.shopImagesViewPager.currentItem = tab.position
        }.attach()
    }

    private fun initViewPagerWithTabLayout() {
        binding.reviewViewPager.adapter = PinAdapter(this)

        TabLayoutMediator(binding.reviewTabLayout, binding.reviewViewPager) { tab, position ->
            binding.reviewViewPager.currentItem = tab.position
            tab.text = resources.getStringArray(R.array.pin_tab)[position]
        }.attach()
    }

    override fun subscribe() {
        observeData()
        viewModel.shopInfo.observe(this) {
            binding.shopTitleTextView.text = it.name
            binding.shopTypeTextView.text = it.category
            binding.reviewStarCountTextView.text = it.ratingCount
            val lastRegisterDate = it.lastReviewDate.split("-")
            binding.lastReviewCountTextView.text = getString(R.string.last_register_date, lastRegisterDate[0], lastRegisterDate[1], lastRegisterDate[2])

            it.photos.forEach {
                binding.shopImagesTabLayout.addTab(binding.shopImagesTabLayout.newTab())
            }
            imageFrameAdapter.submitList(it.photos)
        }
    }

    private fun observeData() {
        viewModel.showProgress.observe(this) {
            binding.progressBarContainer.isVisible = it
        }
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.bookmarkLayer.setOnClickListener {
            binding.bookmarkImageView.isSelected = !binding.bookmarkImageView.isSelected
        }
        binding.reviewButton.setOnClickListener {
            val intent = Intent(this, PinReviewWriteActivity::class.java).apply {
                putExtra("name", viewModel.shopInfo.value?.name ?: return@setOnClickListener)
            }
            reviewWriteResult.launch(intent)
        }
    }
}
