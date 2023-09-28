package com.jjbaksa.jjbaksa.ui.inquiry

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentInquiryAllBinding
import com.jjbaksa.jjbaksa.ui.inquiry.adapter.InquiryAllAdapter
import com.jjbaksa.jjbaksa.ui.inquiry.viewmodel.InquiryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InquiryAllFragment : BaseFragment<FragmentInquiryAllBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_inquiry_all
    private lateinit var inquiryAllAdapter: InquiryAllAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: InquiryViewModel by activityViewModels()

    override fun initView() {
        viewModel.inquiryCursor.value = InquiryCursor.ALL
        viewModel.getInquiry(null, null, 10)
        inquiryAllAdapter = InquiryAllAdapter(requireContext())
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.inquiryAllRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = inquiryAllAdapter
        }
    }

    override fun initEvent() {
        binding.inquiryAllRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val itemCount = linearLayoutManager.itemCount
                    val lastPosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastPosition != -1 && lastPosition >= (itemCount - 1) && viewModel.inquiryHasMore.value == true) {
                        viewModel.inquiryHasMore.value = false
                        viewModel.getInquiry(
                            inquiryAllAdapter.currentList.get(lastPosition)?.id,
                            inquiryAllAdapter.currentList.get(lastPosition)?.createdAt,
                            10
                        )
                        binding.loadingView.setLoading(true)
                    }
                }
            })
    }

    override fun subscribe() {
        viewModel.inquiry.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isEmpty() && inquiryAllAdapter.currentList.isEmpty()) {
                binding.emptyContainer.isVisible = true
                inquiryAllAdapter.submitList(emptyList())
            } else {
                binding.emptyContainer.isVisible = false
                if (viewModel.hasNewInquiry.value == true) {
                    if (it.content.isEmpty()) binding.emptyContainer.isVisible = true
                    viewModel.hasNewInquiry.value = false
                    inquiryAllAdapter.submitList(it.content)
                } else {
                    inquiryAllAdapter.submitList(inquiryAllAdapter.currentList + it.content)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.inquiryCursor.value = InquiryCursor.ALL
    }
}
