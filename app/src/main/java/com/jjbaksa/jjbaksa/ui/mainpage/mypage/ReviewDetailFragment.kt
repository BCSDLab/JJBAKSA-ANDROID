package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentMyPageReviewBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter.ReviewDetailAdapter
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDetailFragment : BaseFragment<FragmentMyPageReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_my_page_review
    private val viewModel: MyPageViewModel by activityViewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val reviewDetailAdapter by lazy {
        ReviewDetailAdapter()
    }

    override fun initView() {
        viewModel.placeId.value = requireArguments().getString("placeId")
        with(binding) {
            titleTextView.text = requireArguments().getString("name")
            categoryTextView.text = requireArguments().getString("category")
        }
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = reviewDetailAdapter
            itemAnimator = null
        }
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener {
            val reviewDetailFragment =
                parentFragmentManager.findFragmentByTag("ReviewDetailFragment")
            if (reviewDetailFragment != null) {
                parentFragmentManager.beginTransaction()
                    .remove(reviewDetailFragment)
                    .commit()
            } else {
                return@setOnClickListener
            }
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val itemCount = linearLayoutManager.itemCount
                val lastPosition = linearLayoutManager.findLastVisibleItemPosition()

                if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.myReviewHasMore.value == true) {
                    viewModel.myReviewHasMore.value = false
                    binding.loadingView.setLoading(true)
                    viewModel.getReviewShopDetail(
                        placeId = viewModel.placeId.value.toString(),
                        idCursor = viewModel.reviewShopDetail.value?.content?.get(lastPosition)?.id
                            ?: return,
                        dateCursor = viewModel.reviewShopDetail.value?.content?.get(lastPosition)?.createdAt
                            ?: return,
                        size = 10
                    )
                }
            }
        })
    }

    override fun subscribe() {
        viewModel.placeId.observe(viewLifecycleOwner) {
            viewModel.getReviewShopDetail(
                placeId = it,
                size = 10
            )
        }
        viewModel.reviewShopDetail.observe(viewLifecycleOwner) {
            if (it.content.isEmpty()) {
                binding.jjNoContentView.isVisible = true
            } else {
                binding.jjNoContentView.isVisible = false
                reviewDetailAdapter.submitList(reviewDetailAdapter.currentList + it.content)
            }
            binding.loadingView.setLoading(false)
        }
    }

    companion object {
        fun newInstance(): ReviewDetailFragment {
            return ReviewDetailFragment()
        }
    }
}
