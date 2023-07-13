package com.jjbaksa.jjbaksa.ui.mainpage.mypage

import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentBookmarkBinding
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_bookmark

    private val viewModel: MyPageViewModel by activityViewModels()

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun subscribe() {
    }
}
