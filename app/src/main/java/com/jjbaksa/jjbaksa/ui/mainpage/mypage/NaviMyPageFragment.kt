package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviMyPageBinding
import com.jjbaksa.jjbaksa.util.setExtendView

class NaviMyPageFragment : BaseFragment<FragmentNaviMyPageBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_navi_my_page

    override fun initView() {
        requireActivity().setExtendView(binding.myPageConstraintLayout)
    }

    override fun initEvent() {
    }

    override fun subscribe() {
    }
}
