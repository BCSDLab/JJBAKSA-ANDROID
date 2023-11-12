package com.jjbaksa.jjbaksa.ui.inquiry

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityInquiryBinding
import com.jjbaksa.jjbaksa.ui.inquiry.viewmodel.InquiryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InquiryActivity : BaseActivity<ActivityInquiryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_inquiry
    private val viewModel: InquiryViewModel by viewModels()

    private val startWriteInquiry =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                viewModel.hasNewInquiry.value = true
                when (viewModel.inquiryCursor.value) {
                    InquiryCursor.ALL -> {
                        viewModel.getInquiry(null, null, 10)
                    }

                    InquiryCursor.MY -> {
                        viewModel.getInquiryMe(null, null, 10)
                    }

                    else -> {}
                }
            }
        }

    override fun initView() {
        binding.view = this
        binding.inquiryViewPager.adapter = InquiryAdapter(this)

        TabLayoutMediator(binding.inquiryTabLayout, binding.inquiryViewPager) { tab, position ->
            binding.inquiryViewPager.currentItem = tab.position
            tab.text = resources.getStringArray(R.array.inquiry_tab)[position]
        }.attach()
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.ivSearch.setOnClickListener {
            binding.etSearch.text?.let {
                if (it.isEmpty()) {
                    showSnackBar(getString(R.string.main_page_search_edit_text_hint))
                } else {
                    viewModel.hasNewInquiry.value = true
                    when (viewModel.inquiryCursor.value) {
                        InquiryCursor.ALL -> {
                            viewModel.getInquirySearch(null, null, 10, it.toString())
                        }
                        InquiryCursor.MY -> {
                            viewModel.getInquirySearchMe(null, null, 10, it.toString())
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun inquiry() {
        val intent = Intent(this, WriteInquiryActivity::class.java)
        startWriteInquiry.launch(intent)
    }
}
