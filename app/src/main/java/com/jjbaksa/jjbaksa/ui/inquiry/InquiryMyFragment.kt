package com.jjbaksa.jjbaksa.ui.inquiry

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
    }

    override fun subscribe() {
        viewModel.inquiryMe.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isNotEmpty()) {
                binding.emptyContainer.isVisible = false
                inquiryMyAdapter.submitList(it.content)
            } else {
                binding.emptyContainer.isVisible = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.inquiryCursor.value = InquiryCursor.MY
    }
}
