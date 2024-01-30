package com.jjbaksa.jjbaksa.ui.pin

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.enums.PinReviewCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPinBinding
import com.jjbaksa.jjbaksa.dialog.DoubleConfirmDialog
import com.jjbaksa.jjbaksa.ui.pin.adapter.ImageFrameAdapter
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinAdapter
import com.jjbaksa.jjbaksa.ui.pin.viewmodel.PinViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.round

@AndroidEntryPoint
class PinActivity : BaseActivity<ActivityPinBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_pin
    private val viewModel: PinViewModel by viewModels()
    private lateinit var imageFrameAdapter: ImageFrameAdapter
    var scrapId: Int = 0

    private val reviewWriteResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                viewModel.getShopDetail(viewModel.placeId.value.toString())
                when (viewModel.pinReviewCursor.value) {
                    PinReviewCursor.MY_REVIEW -> {
                        viewModel.getMyReviewShopLastDate(viewModel.placeId.value.toString())
                    }

                    PinReviewCursor.FOLLOWER_REVIEW -> {
                        viewModel.getShopFollowerReviewLastDate(viewModel.placeId.value.toString())
                    }

                    else -> {}
                }

                if (viewModel.myReviewUpdateCursor.value == MyReviewCursor.LATEST) {
                    viewModel.getMyReview(
                        placeId = viewModel.placeId.value.toString(),
                        size = 10
                    )
                    viewModel.setWriteNewMyReview(true)
                } else if (viewModel.myReviewUpdateCursor.value == MyReviewCursor.STAR) {
                    viewModel.getMyReview(
                        placeId = viewModel.placeId.value.toString(),
                        sort = "rate",
                        size = 10
                    )
                    viewModel.setWriteNewMyReview(true)
                }
            }
        }

    override fun initView() {
        intent.getStringExtra("place_id")?.let {
            viewModel.placeId.value = it
            viewModel.getShopDetail(it)
            viewModel.getShopsRates(it)
            viewModel.getShopsScraps(it)
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
        viewModel.showProgress.observe(this) {
            binding.progressBarContainer.isVisible = it
        }
        viewModel.shopInfo.observe(this) {
            binding.shopTitleTextView.text = it.name
            binding.shopTypeTextView.text = it.category
            it.photos.forEach {
                binding.shopImagesTabLayout.addTab(binding.shopImagesTabLayout.newTab())
            }
            imageFrameAdapter.submitList(it.photos)
        }
        viewModel.rate.flowWithLifecycle(lifecycle).onEach {
            binding.tvReviewStarCount.text = it.toString()
        }.launchIn(lifecycleScope)
        viewModel.scrapId.flowWithLifecycle(lifecycle).onEach {
            binding.bookmarkImageView.isSelected = it != 0L
            scrapId = it.toInt()
        }.launchIn(lifecycleScope)
        viewModel.myReviewLastDate.observe(this) {
            binding.lastReviewCountTextView.text = getString(
                R.string.last_register_date, it.lastDate
            )
        }
        viewModel.friendReviewLastDate.observe(this) {
            binding.lastReviewCountTextView.text = getString(
                R.string.last_register_date, it.lastDate
            )
        }
        viewModel.pinReviewCursor.observe(this) {
            when (it) {
                PinReviewCursor.MY_REVIEW -> {
                    viewModel.getMyReviewShopLastDate(viewModel.placeId.value.toString())
                }

                PinReviewCursor.FOLLOWER_REVIEW -> {
                    viewModel.getShopFollowerReviewLastDate(viewModel.placeId.value.toString())
                }
            }
        }
        viewModel.addScrapInfo.observe(this) {
            if (it.id != 0) {
                binding.bookmarkImageView.isSelected = true
            }
        }
        viewModel.toastMsg.observe(this) { msg ->
            val intent = Intent().apply {
                putExtra("msg", msg)
            }
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.bookmarkLayer.setOnClickListener {
            if (binding.bookmarkImageView.isSelected) {
                DoubleConfirmDialog(
                    title = "북마크 삭제",
                    msg = "해당 음식점을 삭제하시겠습니까?",
                    confirmClick = {
                        // TODO : 스크랩 삭제 API 연동
                        viewModel.deleteShopScrap(scrapId)
                    }
                ).show(supportFragmentManager, SCRAP_REMOVE_DIALOG)
            } else {
                DoubleConfirmDialog(
                    title = "북마크 추가",
                    msg = "해당 음식점을 추가하시겠습니까?",
                    confirmClick = {
                        // TODO : 스크랩 추가 API 연동
                        viewModel.addShopScrap(0, viewModel.placeId.value.toString())
                    }
                ).show(supportFragmentManager, SCRAP_ADD_DIALOG)
            }
        }
        binding.reviewButton.setOnClickListener {
            val intent = Intent(this, PinReviewWriteActivity::class.java).apply {
                putExtra("name", viewModel.shopInfo.value?.name ?: return@setOnClickListener)
                putExtra("place_id", viewModel.placeId.value ?: return@setOnClickListener)
            }
            reviewWriteResult.launch(intent)
        }
    }

    companion object {
        const val SCRAP_ADD_DIALOG = "SCRAP_ADD_DIALOG"
        const val SCRAP_REMOVE_DIALOG = "SCRAP_REMOVE_DIALOG"
    }
}
