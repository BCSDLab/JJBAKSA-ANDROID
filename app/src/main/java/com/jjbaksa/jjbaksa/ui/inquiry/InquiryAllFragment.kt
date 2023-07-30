package com.jjbaksa.jjbaksa.ui.inquiry

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentInquiryAllBinding
import com.jjbaksa.jjbaksa.ui.inquiry.viewmodel.InquiryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InquiryAllFragment : BaseFragment<FragmentInquiryAllBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_inquiry_all
    private val viewModel: InquiryViewModel by activityViewModels()

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun subscribe() {
    }
}