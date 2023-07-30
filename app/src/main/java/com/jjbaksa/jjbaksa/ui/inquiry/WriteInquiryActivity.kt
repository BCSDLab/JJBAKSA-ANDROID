package com.jjbaksa.jjbaksa.ui.inquiry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityWriteInquiryBinding
import com.jjbaksa.jjbaksa.util.KeyboardProvider

class WriteInquiryActivity : BaseActivity<ActivityWriteInquiryBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_write_inquiry

    override fun initView() {
        KeyboardProvider(this).inputKeyboardResize(window, binding.root)
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
    }
}