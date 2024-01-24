package com.jjbaksa.jjbaksa.ui.findpassword

import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityFindPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPasswordActivity : BaseActivity<ActivityFindPasswordBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_find_password

    override fun initView() {
        binding.lifecycleOwner = this
    }

    override fun subscribe() {}

    override fun initEvent() {}
}
