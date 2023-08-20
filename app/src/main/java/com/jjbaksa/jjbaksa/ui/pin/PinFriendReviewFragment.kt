package com.jjbaksa.jjbaksa.ui.pin

import android.content.Intent
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.FriendReviewCursor
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
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun initView() {
        linearLayoutManager = LinearLayoutManager(context)
        binding.friendReviewRecyclerView.apply {
            layoutManager = linearLayoutManager
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
        binding.friendReviewRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val itemCount = linearLayoutManager.itemCount
                val lastPosition = linearLayoutManager.findLastVisibleItemPosition()

                if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.friendReviewHasMore.value == true) {
                    viewModel.friendReviewHasMore.value = false
                    Log.e("로그", "?")
                    when (viewModel.friendReviewUpdateCursor.value) {
                        FriendReviewCursor.LATEST -> {
                            viewModel.getFollowerShopReview(
                                placeId = viewModel.placeId.value.toString(),
                                idCursor = viewModel.friendReview.value?.content?.get(lastPosition)?.id ?: return,
                                dateCursor = viewModel.friendReview.value?.content?.get(lastPosition)?.createdAt ?: return,
                                size = 10
                            )
                        }
                        FriendReviewCursor.STAR -> {
                            viewModel.getFollowerShopReview(
                                placeId = viewModel.placeId.value.toString(),
                                idCursor = viewModel.friendReview.value?.content?.get(lastPosition)?.id ?: return,
                                rateCursor = viewModel.friendReview.value?.content?.get(lastPosition)?.rate ?: return,
                                sort = "rate",
                                size = 10
                            )
                        }
                        else -> {}
                    }
                }
            }
        })
    }

    override fun subscribe() {
        viewModel.friendReview.observe(viewLifecycleOwner) {
            if (it.content.isEmpty() && friendReviewAdapter.currentList.isEmpty()) {
                binding.friendReviewEmptyContainer.isVisible = true
            } else {
                binding.friendReviewEmptyContainer.isVisible = false
                friendReviewAdapter.submitList(friendReviewAdapter.currentList + it.content)
            }
        }
        viewModel.friendReviewUpdateCursor.observe(viewLifecycleOwner) {
            when (it) {
                FriendReviewCursor.LATEST -> {
                    friendReviewAdapter.submitList(emptyList())
                    binding.updateReviewTextView.text = getString(R.string.newest)
                    viewModel.getFollowerShopReview(
                        placeId = viewModel.placeId.value.toString(),
                        size = 10
                    )
                }

                FriendReviewCursor.STAR -> {
                    friendReviewAdapter.submitList(emptyList())
                    binding.updateReviewTextView.text = getString(R.string.by_star_rating)
                    viewModel.getFollowerShopReview(
                        placeId = viewModel.placeId.value.toString(),
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
