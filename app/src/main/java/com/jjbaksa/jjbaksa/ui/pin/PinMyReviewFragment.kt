package com.jjbaksa.jjbaksa.ui.pin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.domain.enums.MyReviewCursor
import com.jjbaksa.domain.enums.PinReviewCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentPinMyReviewBinding
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinMyReviewAdapter
import com.jjbaksa.jjbaksa.ui.pin.viewmodel.PinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinMyReviewFragment : BaseFragment<FragmentPinMyReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_pin_my_review
    private val viewModel: PinViewModel by activityViewModels()
    private lateinit var myReviewAdapter: PinMyReviewAdapter

    override fun initView() {
        myReviewAdapter = PinMyReviewAdapter()
        binding.myReviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myReviewAdapter
            itemAnimator = null
        }
        viewModel.myReviewUpdateCursor.value = MyReviewCursor.LATEST
        viewModel.pinReviewCursor.value = PinReviewCursor.MY_REVIEW
    }

    override fun initEvent() {
        binding.updateLayer.setOnClickListener {
            when (viewModel.myReviewUpdateCursor.value) {
                MyReviewCursor.LATEST -> {
                    viewModel.myReviewUpdateCursor.value = MyReviewCursor.STAR
                }

                MyReviewCursor.STAR -> {
                    viewModel.myReviewUpdateCursor.value = MyReviewCursor.LATEST
                }

                else -> {}
            }
        }
    }

    override fun subscribe() {
        viewModel.myReview.observe(viewLifecycleOwner) {
            if (it.content.isEmpty()) {
                binding.myReviewEmptyContainer.isVisible = true
            } else {
                binding.myReviewEmptyContainer.isVisible = false
                myReviewAdapter.submitList(it.content)
            }
        }
        viewModel.myReviewUpdateCursor.observe(viewLifecycleOwner) {
            when (it) {
                MyReviewCursor.LATEST -> {
                    binding.updateReviewTextView.text = getString(R.string.newest)
                    viewModel.getMyReview(
                        placeId = viewModel.placeId.value.toString(),
                        size = 10
                    )
                }

                MyReviewCursor.STAR -> {
                    binding.updateReviewTextView.text = getString(R.string.by_star_rating)
                    viewModel.getMyReview(
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
        viewModel.pinReviewCursor.value = PinReviewCursor.MY_REVIEW
    }
}
