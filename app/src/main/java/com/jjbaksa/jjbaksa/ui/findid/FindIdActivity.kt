package com.jjbaksa.jjbaksa.ui.findid

import androidx.activity.viewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityFindIdBinding
import com.jjbaksa.jjbaksa.viewmodel.FindIdViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindIdActivity : BaseActivity<ActivityFindIdBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_find_id


    override fun initView() {
        binding.lifecycleOwner = this
    }

    override fun subscribe() {
    }

    override fun initEvent() {
    }
}