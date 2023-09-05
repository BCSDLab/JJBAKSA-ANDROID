package com.jjbaksa.jjbaksa.ui.inquiry

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val inquiryAllAdapter: InquiryAllAdapter by lazy {
        InquiryAllAdapter(requireContext())
    }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: InquiryViewModel by activityViewModels()

    override fun initView() {
        viewModel.getInquiry(null, null, 20)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.inquiryAllRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = inquiryAllAdapter
        }
    }

    override fun initEvent() {}

    override fun subscribe() {
        viewModel.inquiry.observe(viewLifecycleOwner) {
            binding.loadingView.setLoading(false)
            if (it.content.isNotEmpty()) {
                inquiryAllAdapter.submitList(it.content)
            }
        }
    }
}
