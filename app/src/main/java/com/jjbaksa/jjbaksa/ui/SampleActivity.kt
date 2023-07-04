package com.jjbaksa.jjbaksa.ui

import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySampleBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog

class SampleActivity : BaseActivity<ActivitySampleBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_sample

    override fun initView() {
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            confirmDialogButton.setOnClickListener {
                ConfirmDialog("타이틀", "메시지 입니다", "확인", { it.dismiss() })
            }
        }
    }
}
