package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.model.review.ReviewShopContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentReviewBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter.ReviewAdapter
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.fromDpToPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review
    private val reviewDetailFragment by lazy {
        ReviewDetailFragment.newInstance()
    }

    private val reviewAdapter: ReviewAdapter by lazy {
        ReviewAdapter(::goToShopDetail)
    }
    private lateinit var gridLayoutManager: GridLayoutManager
    private val viewModel: MyPageViewModel by activityViewModels()

    override fun initView() {
        viewModel.getReviewShop(null, 10)
        gridLayoutManager = GridLayoutManager(context, 3)
        binding.reviewRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = reviewAdapter
            val spanCount = 3
            val space = 20f.fromDpToPx()
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State,
                ) {
                    val position = parent.getChildAdapterPosition(view)

                    if (position >= 0) {
                        val column = position % spanCount
                        outRect.apply {
                            left = space - column * space / spanCount
                            right = (column + 1) * space / spanCount
                            bottom = space
                        }
                    }
                }
            })
        }
    }

    override fun initEvent() {
        refreshReview()
    }

    private fun refreshReview() {
        binding.swipeRlContainer.setOnRefreshListener {
            viewModel.getReviewShop(null, 10)
        }
    }

    override fun subscribe() {
        viewModel.reviewShops.observe(viewLifecycleOwner) {
            binding.progressContainer.isVisible = false
            binding.swipeRlContainer.isRefreshing = false
            if (it.content.isNullOrEmpty()) {
                binding.jjNoContentView.isVisible = true
            } else {
                binding.jjNoContentView.isVisible = false
                reviewAdapter.submitList(it.content)
                binding.totalReviewTextView.text =
                    getString(R.string.total_review, MyInfo.reviews.toString())
            }
        }
    }

    private fun goToShopDetail(reviewShopContent: ReviewShopContent) {
        val bundle = Bundle()
        bundle.putString("placeId", reviewShopContent.placeId)
        bundle.putString("name", reviewShopContent.name)
        bundle.putString("category", reviewShopContent.category)
        reviewDetailFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container, reviewDetailFragment, ReviewDetailFragment.TAG)
            .commit()
    }
}
