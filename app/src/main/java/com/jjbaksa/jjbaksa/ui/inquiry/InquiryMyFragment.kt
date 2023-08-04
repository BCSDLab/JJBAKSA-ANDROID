package com.jjbaksa.jjbaksa.ui.inquiry

import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentInquiryMyBinding
import com.jjbaksa.jjbaksa.ui.inquiry.viewmodel.InquiryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InquiryMyFragment : BaseFragment<FragmentInquiryMyBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_inquiry_my
    private val viewModel: InquiryViewModel by activityViewModels()

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun subscribe() {
    }
}
