package com.jjbaksa.jjbaksa.ui.shop

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityShopBinding
import com.jjbaksa.jjbaksa.dialog.DoubleConfirmDialog
import com.jjbaksa.jjbaksa.ui.pin.PinActivity
import com.jjbaksa.jjbaksa.ui.pin.adapter.ImageFrameAdapter
import com.jjbaksa.jjbaksa.ui.shop.viewmodel.ShopViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class ShopActivity : BaseActivity<ActivityShopBinding>(), OnMapReadyCallback {
    override val layoutId: Int
        get() = R.layout.activity_shop
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var imageFrameAdapter: ImageFrameAdapter

    override fun initView() {
        binding.shop = viewModel
        binding.lifecycleOwner = this
        intent.getStringExtra("place_id")?.let {
            viewModel.placeId.value = it
            viewModel.getShopInfo(it)
        }
        viewModel.getMyReview(
            placeId = viewModel.placeId.value.toString(),
            size = 2
        )
        viewModel.getFollowerShopReview(
            placeId = viewModel.placeId.value.toString(),
            size = 2
        )
        viewModel.getFollowersShopReviewCount(
            placeId = viewModel.placeId.value.toString()
        )
        viewModel.getMyLastReviewDate(
            placeId = viewModel.placeId.value.toString()
        )
        viewModel.getShopRates(
            placeId = viewModel.placeId.value.toString()
        )
        initShopImageViewPagerWithTabLayout()
        initMap()
    }

    private fun initMap() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_view) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_view, it).commit()
            }
        mapFragment.getMapAsync(this)
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
            binding.addressTextView.text = it.formattedAddress
            binding.phoneTextView.text = it.formattedPhoneNumber

//            binding.bookmarkImageView.isSelected = it.scrap != 0

            it.photos.forEach {
                binding.shopImagesTabLayout.addTab(binding.shopImagesTabLayout.newTab())
            }
            imageFrameAdapter.submitList(it.photos)
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
        viewModel.shopAverageRate.observe(this) {
            binding.tvReviewStarCount.text = String.format("%.1f", it)
        }
        observeMyReview()
        observeFriendsReview()
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.bookmarkLayer.setOnClickListener {
            if (binding.bookmarkImageView.isSelected) {
                DoubleConfirmDialog(
                    title = "북마크 삭제",
                    msg = "해당 음식점을 북마크에서 삭제할까요?",
                    confirmClick = {
                        viewModel.deleteShopScrap(viewModel.addScrapInfo.value?.id ?: return@DoubleConfirmDialog)
                        showSnackBar("북마크가 삭제되었어요")
                    }
                ).show(supportFragmentManager, SCRAP_REMOVE_DIALOG)
            } else {
                DoubleConfirmDialog(
                    title = "북마크 추가",
                    msg = "해당 음식점을 북마크에 추가할까요?",
                    confirmClick = {
                        viewModel.addShopScrap(0, viewModel.placeId.value.toString())
                        showSnackBar("북마크가 추가되었어요")
                    }
                ).show(supportFragmentManager, SCRAP_ADD_DIALOG)
            }
        }
        binding.seeAllFriendsReviewTextView.setOnClickListener {
            val intent = Intent(this, PinActivity::class.java)
            intent.putExtra("place_id", viewModel.placeId.value)
            startActivity(intent)
        }
        binding.seeAllMyReviewTextView.setOnClickListener {
            val intent = Intent(this, PinActivity::class.java)
            intent.putExtra("place_id", viewModel.placeId.value)
            startActivity(intent)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(
                viewModel.shopInfo.value?.coordinate?.lat ?: 0.0,
                viewModel.shopInfo.value?.coordinate?.lng ?: 0.0
            )
        )
        naverMap.moveCamera(cameraUpdate)
    }

    companion object {
        const val SCRAP_ADD_DIALOG = "SCRAP_ADD_DIALOG"
        const val SCRAP_REMOVE_DIALOG = "SCRAP_REMOVE_DIALOG"
    }

    private fun observeMyReview() {
        viewModel.myReview.observe(this) {
            if (it.content.isNotEmpty()) {
                binding.myReviewLayout1.myReviewContentTextView.text = it.content[0].content
                binding.myReviewLayout1.myReviewCreatedDateTextView.text = it.content[0].createdAt
                binding.myReviewLayout1.tvReviewStarCount.text = it.content[0].rate.toString()

                if (it.content.size > 1) {
                    binding.myReviewLayout2.myReviewContentTextView.text = it.content[1].content
                    binding.myReviewLayout2.myReviewCreatedDateTextView.text = it.content[1].createdAt
                    binding.myReviewLayout2.tvReviewStarCount.text = it.content[1].rate.toString()
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
    }
    private fun observeFriendsReview() {
        viewModel.friendReview.observe(this) {
            if (it.content.isNotEmpty()) {
                binding.friendReviewLayout1.friendReviewNameTextView.text = it.content[0].userReviewResponse.nickname
                binding.friendReviewLayout1.friendReviewAccountTextView.text = it.content[0].userReviewResponse.account
                binding.friendReviewLayout1.friendReviewContentTextView.text = it.content[0].content
                binding.friendReviewLayout1.friendReviewCreatedDateTextView.text = it.content[0].createdAt
                binding.friendReviewLayout1.tvReviewStarCount.text = it.content[0].rate.toString()
                Glide.with(this)
                    .load(it.content[0].userReviewResponse.profileImage.url)
                    .placeholder(R.drawable.ic_profile)
                    .circleCrop()
                    .into(binding.friendReviewLayout1.friendReviewProfileImageView)

                if (it.content.size > 1) {
                    binding.friendReviewLayout2.friendReviewNameTextView.text = it.content[1].userReviewResponse.nickname
                    binding.friendReviewLayout2.friendReviewAccountTextView.text = it.content[1].userReviewResponse.account
                    binding.friendReviewLayout2.friendReviewContentTextView.text = it.content[1].content
                    binding.friendReviewLayout2.friendReviewCreatedDateTextView.text = it.content[1].createdAt
                    binding.friendReviewLayout2.tvReviewStarCount.text = it.content[1].rate.toString()
                    Glide.with(this)
                        .load(it.content[1].userReviewResponse.profileImage.url)
                        .placeholder(R.drawable.ic_profile)
                        .circleCrop()
                        .into(binding.friendReviewLayout2.friendReviewProfileImageView)
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
}
