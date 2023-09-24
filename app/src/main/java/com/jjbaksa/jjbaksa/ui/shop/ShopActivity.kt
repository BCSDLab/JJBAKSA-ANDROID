package com.jjbaksa.jjbaksa.ui.shop

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.domain.enums.PinReviewCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityShopBinding
import com.jjbaksa.jjbaksa.dialog.DoubleConfirmDialog
import com.jjbaksa.jjbaksa.ui.pin.PinReviewWriteActivity
import com.jjbaksa.jjbaksa.ui.pin.adapter.ImageFrameAdapter
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinAdapter
import com.jjbaksa.jjbaksa.ui.shop.viewmodel.ShopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class ShopActivity : BaseActivity<ActivityShopBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_shop
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var imageFrameAdapter: ImageFrameAdapter

    override fun initView() {
        intent.getStringExtra("place_id")?.let {
            viewModel.placeId.value = it
            viewModel.getShopDetail(it)
        }
        viewModel.getMyReview(
            placeId = viewModel.placeId.value.toString(),
            size = 2
        )
        viewModel.getFollowerShopReview(
            placeId = viewModel.placeId.value.toString(),
            size = 2
        )
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


    override fun subscribe() {
        viewModel.showProgress.observe(this) {
            binding.progressBarContainer.isVisible = it
        }
        viewModel.shopInfo.observe(this) {
            binding.shopTitleTextView.text = it.name
            binding.shopTypeTextView.text = it.category
            binding.reviewStarCountTextView.text =
                round((it.totalRating / it.ratingCount.toDouble()) * 10).div(10).toString()
            binding.bookmarkImageView.isSelected = it.scrap != 0

            it.photos.forEach {
                binding.shopImagesTabLayout.addTab(binding.shopImagesTabLayout.newTab())
            }
            imageFrameAdapter.submitList(it.photos)

            binding.addressTextView.text = it.formattedAddress
            binding.phoneTextView.text = it.formattedPhoneNumber
            binding.scheduleTextView.text = it.period.first().open.time.toString() + " ~ " + it.period.first().close.time.toString()
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
        viewModel.myReview.observe(this) {
            if (it.content.isNotEmpty()) {
                binding.myReviewLayout1.myReviewContentTextView.text = it.content[0].content
                binding.myReviewLayout1.myReviewCreatedDateTextView.text = it.content[0].createdAt
                binding.myReviewLayout1.reviewStarCountTextView.text = it.content[0].rate.toString()

                if(it.content.size > 1) {
                    binding.myReviewLayout2.myReviewContentTextView.text = it.content[1].content
                    binding.myReviewLayout2.myReviewCreatedDateTextView.text = it.content[1].createdAt
                    binding.myReviewLayout2.reviewStarCountTextView.text = it.content[1].rate.toString()
                } else {
                    binding.myReviewLayout2.root.visibility = View.GONE
                }
            } else {
                binding.myReviewLayout1.root.visibility = View.GONE
                binding.myReviewLayout2.root.visibility = View.GONE
                binding.emptyMyReviewImageView.visibility = View.VISIBLE
                binding.emptyMyReviewTextView.visibility = View.VISIBLE
            }
        }
        viewModel.friendReview.observe(this) {
            if (it.content.isNotEmpty()) {
                binding.friendReviewLayout1.friendReviewContentTextView.text = it.content[0].content
                binding.friendReviewLayout1.friendReviewCreatedDateTextView.text = it.content[0].createdAt
                binding.friendReviewLayout1.reviewStarCountTextView.text = it.content[0].rate.toString()

                if(it.content.size > 1) {
                    binding.friendReviewLayout2.friendReviewContentTextView.text = it.content[1].content
                    binding.friendReviewLayout2.friendReviewCreatedDateTextView.text = it.content[1].createdAt
                    binding.friendReviewLayout2.reviewStarCountTextView.text = it.content[1].rate.toString()
                } else {
                    binding.friendReviewLayout2.root.visibility = View.GONE
                }
            } else {
                binding.friendReviewLayout1.root.visibility = View.GONE
                binding.friendReviewLayout2.root.visibility = View.GONE
                binding.emptyFriendReviewImageView.visibility = View.VISIBLE
                binding.emptyFriendReviewTextView.visibility = View.VISIBLE
            }
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
        binding.seeAllFriendsReviewTextView.setOnClickListener {
            // TODO : 친구 리뷰 전체보기
        }
        binding.seeAllMyReviewTextView.setOnClickListener {
            // TODO : 내 리뷰 전체보기
        }
    }

    companion object {
        const val SCRAP_ADD_DIALOG = "SCRAP_ADD_DIALOG"
        const val SCRAP_REMOVE_DIALOG = "SCRAP_REMOVE_DIALOG"
    }
}