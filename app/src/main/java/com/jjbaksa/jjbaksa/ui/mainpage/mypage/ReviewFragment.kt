package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentReviewBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter.ReviewAdapter
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review

    private val reviewAdapter: ReviewAdapter by lazy {
        ReviewAdapter()
    }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: MyPageViewModel by activityViewModels()

    override fun initView() {
        viewModel.getReviewShop(null, 10)
        linearLayoutManager = LinearLayoutManager(context)
        binding.reviewRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = reviewAdapter
        }
    }

    override fun initEvent() {
    }

    override fun subscribe() {
        viewModel.reviewShops.observe(viewLifecycleOwner) {
            binding.progressContainer.isVisible = false
            if (it.content.isNullOrEmpty()) {
                binding.jjNoContentView.isVisible = true
            } else {
                binding.jjNoContentView.isVisible = false
                reviewAdapter.submitList(it.content)
            }
        }
    }
}
