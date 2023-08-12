package com.jjbaksa.jjbaksa.ui.pin

import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPinMyFriendReviewReportBinding

class PinMyFriendReviewReportActivity: BaseActivity<ActivityPinMyFriendReviewReportBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_pin_my_friend_review_report

    override fun initView() {
        intent.getStringExtra("name").also {
            binding.titleTextView.text = it
        }
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
    }
}
