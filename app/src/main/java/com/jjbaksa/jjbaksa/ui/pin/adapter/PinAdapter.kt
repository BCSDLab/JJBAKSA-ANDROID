package com.jjbaksa.jjbaksa.ui.pin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jjbaksa.jjbaksa.ui.pin.PinFriendReviewFragment
import com.jjbaksa.jjbaksa.ui.pin.PinMyReviewFragment

class PinAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> PinMyReviewFragment()
        else -> PinFriendReviewFragment()
    }
}
