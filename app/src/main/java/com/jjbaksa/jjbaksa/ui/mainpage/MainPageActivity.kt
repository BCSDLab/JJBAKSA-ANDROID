package com.jjbaksa.jjbaksa.ui.mainpage

import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    private val naviFragmentHome by lazy { NaviHomeFragment.newInstance() }
    private val naviFragmentWrite by lazy { NaviWriteFragment.newInstance() }
    private val naviFragmentMyPage by lazy { NaviMyPageFragment.newInstance() }
    override val layoutId: Int
        get() = R.layout.activity_main_page

    override fun initView() {
        binding.lifecycleOwner = this
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding.navigationView.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homeItem -> {
                        changeFragment(naviFragmentHome)
                    }
                    R.id.writeItem -> {
                        changeFragment(naviFragmentWrite)
                    }
                    R.id.myPageItem -> {
                        changeFragment(naviFragmentMyPage)
                    }
                }
                true
            }
            selectedItemId = R.id.homeItem
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    override fun onBackPressed() {
    }
}
