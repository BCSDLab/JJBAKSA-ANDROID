package com.jjbaksa.jjbaksa.ui.pin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.domain.enums.FriendReviewCursor
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.enums.PinReviewCursor
import com.jjbaksa.domain.resp.follower.FollowerShopReviewContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentPinFriendReviewBinding
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinFriendReviewAdapter
import com.jjbaksa.jjbaksa.ui.pin.viewmodel.PinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinFriendReviewFragment : BaseFragment<FragmentPinFriendReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_pin_friend_review
    private val viewModel: PinViewModel by activityViewModels()
    private val friendReviewAdapter: PinFriendReviewAdapter by lazy {
        PinFriendReviewAdapter(::onReport)
    }

    override fun initView() {
        binding.friendReviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = friendReviewAdapter
            itemAnimator = null
        }
        viewModel.friendReviewUpdateCursor.value = FriendReviewCursor.LATEST
    }

    override fun initEvent() {
        binding.updateLayer.setOnClickListener {
            when (viewModel.friendReviewUpdateCursor.value) {
                FriendReviewCursor.LATEST -> {
                    viewModel.friendReviewUpdateCursor.value = FriendReviewCursor.STAR
                }

                FriendReviewCursor.STAR -> {
                    viewModel.friendReviewUpdateCursor.value = FriendReviewCursor.LATEST
                }

                else -> {}
            }
        }
    }

    override fun subscribe() {
        viewModel.friendReview.observe(viewLifecycleOwner) {
            if (it.content.isEmpty()) {
                binding.friendReviewEmptyContainer.isVisible = true
            } else {
                binding.friendReviewEmptyContainer.isVisible = false
                friendReviewAdapter.submitList(it.content)
            }
        }
        viewModel.friendReviewUpdateCursor.observe(viewLifecycleOwner) {
            when (it) {
                FriendReviewCursor.LATEST -> {
                    binding.updateReviewTextView.text = getString(R.string.newest)
                    viewModel.getFollowerShopReview(
                        placeId = "ChIJBahxzkWjfDUR7iD24mIMTHU",
                        // placeId = viewModel.placeId.value.toString()
                        size = 10
                    )
                }

                FriendReviewCursor.STAR -> {
                    binding.updateReviewTextView.text = getString(R.string.by_star_rating)
                    viewModel.getFollowerShopReview(
                        placeId = "ChIJBahxzkWjfDUR7iD24mIMTHU",
                        // placeId = viewModel.placeId.value.toString()
                        sort = "rate",
                        size = 10
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.pinReviewCursor.value = PinReviewCursor.FOLLOWER_REVIEW
    }

    private fun onReport(followerReviewInfo: FollowerShopReviewContent) {
        Log.e("로그", "$followerReviewInfo")
        val intent = Intent(requireContext(), PinMyFriendReviewReportActivity::class.java).apply {
            putExtra("name", viewModel.shopInfo.value?.name)
        }
        startActivity(intent)
    }
}
