package com.jjbaksa.jjbaksa.ui.search

import android.content.Intent
import androidx.activity.viewModels
import com.jjbaksa.domain.base.BaseState
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityTrendInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendInfoActivity : BaseActivity<ActivityTrendInfoBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_trend_info
    val viewModel: TrendingInfoViewModel by viewModels()

    override fun initView() {
        viewModel.getTrendKeywordList()
    }

    override fun subscribe() {
        with(viewModel) {
            trendKeywordState.observe(this@TrendInfoActivity) {
                when (it) {
                    is BaseState.Uninitialized -> {}
                    is BaseState.Loading -> {
                    }
                    is BaseState.Success<*> -> {
                        val resp = it.SuccessResp as List<String>
                        var trendingText = ""
                        resp.forEach { trendingText += it }
                        binding.trendListTv.text = trendingText
                        binding.trendListTv.isSelected = true
                    }
                    is BaseState.Fail<*> -> {
                        val resp = it.FailResp as String
                        shortToast(resp)
                    }
                }
            }
        }
    }

    override fun initEvent() {
        with(binding) {
            binding.trendSearchCl.setOnClickListener {
                Intent(this@TrendInfoActivity, SearchActivity::class.java).also { startActivity(it) }
            }
        }
    }
}
