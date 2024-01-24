package com.jjbaksa.jjbaksa.ui.mainpage.mypage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.BookmarkFragment
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.ReviewFragment

class MyPageAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> ReviewFragment()
        else -> BookmarkFragment()
    }
}
