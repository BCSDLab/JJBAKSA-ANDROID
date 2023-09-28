package com.jjbaksa.jjbaksa.ui.inquiry

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentInquiryMyBinding
import com.jjbaksa.jjbaksa.ui.inquiry.adapter.InquiryMyAdapter
import com.jjbaksa.jjbaksa.ui.inquiry.viewmodel.InquiryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InquiryMyFragment : BaseFragment<FragmentInquiryMyBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_inquiry_my
    private val viewModel: InquiryViewModel by activityViewModels()
    private lateinit var inquiryMyAdapter: InquiryMyAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun initView() {
        viewModel.inquiryCursor.value = InquiryCursor.MY
        viewModel.getInquiryMe(null, null, 10)
        inquiryMyAdapter = InquiryMyAdapter(requireContext())
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.inquiryMyRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = inquiryMyAdapter
        }
    }

    override fun initEvent() {
        binding.inquiryMyRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = linearLayoutManager.itemCount
                    val lastPosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.inquiryMeHasMore.value == true) {
                        viewModel.inquiryMeHasMore.value = false
                        viewModel.getInquiryMe(
                            inquiryMyAdapter.currentList.get(lastPosition)?.id,
                            inquiryMyAdapter.currentList.get(lastPosition)?.createdAt,
                            10
                        )
                        binding.loadingView.setLoading(true)
                    }
                }
            })
    }

    override fun subscribe() {
        viewModel.inquiryMe.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && inquiryMyAdapter.currentList.isEmpty()) {
                binding.emptyContainer.isVisible = true
                inquiryMyAdapter.submitList(emptyList())
            } else {
                binding.emptyContainer.isVisible = false
                if (viewModel.hasNewInquiry.value == true) {
                    inquiryMyAdapter.submitList(it.content)
                } else {
                    inquiryMyAdapter.submitList(inquiryMyAdapter.currentList + it.content)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.inquiryCursor.value = InquiryCursor.MY
    }
}
