package com.jjbaksa.jjbaksa.ui.pin

import android.util.Log
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityPinBinding
import com.jjbaksa.jjbaksa.ui.pin.adapter.PinAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinActivity: BaseActivity<ActivityPinBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_pin

    override fun initView() {
        initViewPagerWithTabLayout()
        val placeId = intent.getStringExtra("place_id")
        Log.e("로그", "id : $placeId")
    }

    private fun initViewPagerWithTabLayout() {
        binding.reviewViewPager.adapter = PinAdapter(this)

        TabLayoutMediator(binding.reviewTabLayout, binding.reviewViewPager) { tab, position ->
            binding.reviewViewPager.currentItem = tab.position
            tab.text = resources.getStringArray(R.array.pin_tab)[position]
        }.attach()
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.bookmarkLayer.setOnClickListener {
            // TODO: 북마크 클릭 시 이벤트 처리
        }
        binding.reviewButton.setOnClickListener {
            // TODO: 리뷰 작성하기 탭으로 이동
        }
    }
}