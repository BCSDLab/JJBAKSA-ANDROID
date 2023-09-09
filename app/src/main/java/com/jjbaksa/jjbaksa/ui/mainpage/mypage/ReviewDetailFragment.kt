package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentMyPageReviewBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDetailFragment : BaseFragment<FragmentMyPageReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_my_page_review
    private val viewModel: MyPageViewModel by activityViewModels()

    override fun initView() {
        viewModel.placeId.value = requireArguments().getString("placeId")
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
    }

    override fun subscribe() {
        viewModel.placeId.observe(viewLifecycleOwner) {
            binding.titleTextView.text = it
            binding.categoryTextView.text = it
        }
    }

    companion object {
        fun newInstance(): ReviewDetailFragment {
            return ReviewDetailFragment()
        }
    }
}
