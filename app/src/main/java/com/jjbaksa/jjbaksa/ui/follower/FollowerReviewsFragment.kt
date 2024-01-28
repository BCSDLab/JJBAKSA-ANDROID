package com.jjbaksa.jjbaksa.ui.follower

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.jjbaksa.domain.model.review.MyReviewShopsContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFollowerReviewsBinding
import com.jjbaksa.jjbaksa.ui.follower.adapter.FollowerReviewedShopAdapter
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FollowerReviewsFragment : BaseFragment<FragmentFollowerReviewsBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_follower_reviews
    private val viewModel: FollowerProfileViewModel by activityViewModels()
    private val followerReviewedShopAdapter: FollowerReviewedShopAdapter by lazy {
        FollowerReviewedShopAdapter { shop, adapter ->
            viewModel.getShopReview(viewModel.fid, shop.placeId) {
                adapter.submitList(it.content.map {
                    MyReviewShopsContent(
                        id = it.id,
                        content = it.content,
                        rate = it.rate,
                        createdAt = it.createdAt,
                    )
                })
            }
        }
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvFollowerReviewShops.adapter = followerReviewedShopAdapter
        viewModel.getFollowerReviewCount()
        viewModel.getReviewedShops()
    }

    override fun subscribe() {
        viewModel.reviewedShops.observe(viewLifecycleOwner) {
            followerReviewedShopAdapter.submitList(it.content)
        }
    }

    override fun initEvent() {

    }
}