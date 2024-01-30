package com.jjbaksa.jjbaksa.ui.follower

import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityFollowerProfileBinding
import com.jjbaksa.jjbaksa.ui.follower.adapter.PageAdapter
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerProfileActivity : BaseActivity<ActivityFollowerProfileBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_follower_profile
    private val viewModel: FollowerProfileViewModel by viewModels()

    private val nickname: String by lazy {
        intent.getStringExtra("nickname") ?: ""
    }
    private val account: String by lazy {
        intent.getStringExtra("account") ?: ""
    }
    private val followerCount: Int by lazy {
        intent.getIntExtra("followerCount", 0)
    }
    private val fid: Long by lazy {
        intent.getLongExtra("fid", 0)
    }
    private val profileImage: String by lazy {
        intent.getStringExtra("profileImage") ?: ""
    }

    override fun initView() {
        binding.lifecycleOwner = this
        binding.run {
            tvNickname.text = nickname
            tvAccount.text = "@" + account
            tvFollowerCount.text = followerCount.toString()
            Glide.with(this@FollowerProfileActivity)
                .load(profileImage)
                .error(R.drawable.baseline_supervised_user_circle_24)
                .circleCrop()
                .into(ivProfile)
        }
        viewModel.fid = fid
        initTabLayoutAndViewPager()
    }
    private fun initTabLayoutAndViewPager() {
        binding.vpPage.adapter = PageAdapter(this)

        TabLayoutMediator(
            binding.tlPage,
            binding.vpPage
        ) { tab, position ->
            binding.vpPage.currentItem = tab.position
            tab.text = resources.getStringArray(R.array.my_page_tab)[position]
        }.attach()
    }

    override fun subscribe() {
    }

    override fun initEvent() {
    }
}
