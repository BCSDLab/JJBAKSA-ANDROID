package com.jjbaksa.jjbaksa.ui.mainpage

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityMainPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageActivity : BaseActivity<ActivityMainPageBinding>() {
    private val naviFragmentHome by lazy { NaviHomeFragment() }
    private val naviFragmentWrite by lazy { NaviWriteFragment() }
    private val naviFragmentMyPage by lazy { NaviMyPageFragment() }
    private var isFloatingMenuButtonClicked: Boolean = false
    override val layoutId: Int
        get() = R.layout.activity_main_page

    override fun initView() {
        binding.lifecycleOwner = this
    }

    override fun subscribe() {
    }

    override fun initEvent() {
        initNavigationBar()
        setFloatingMenuInvisible()

        binding.buttonHamburger.setOnClickListener {
            if (isFloatingMenuButtonClicked) {
                setFloatingMenuInvisible()
            } else setFloatingMenuVisible()
        }
    }

    private fun setFloatingMenuInvisible() {
        with(binding) {
            buttonBookmark.visibility = View.GONE
            buttonFriend.visibility = View.GONE
            buttonMap.visibility = View.GONE

            textViewBookmark.visibility = View.GONE
            textViewFriend.visibility = View.GONE
            textViewFindStore.visibility = View.GONE

            imageButtonPlus.visibility = View.VISIBLE
            imageButtonMinus.visibility = View.VISIBLE
        }
        isFloatingMenuButtonClicked = false
    }

    private fun setFloatingMenuVisible() {
        with(binding) {
            buttonBookmark.visibility = View.VISIBLE
            buttonFriend.visibility = View.VISIBLE
            buttonMap.visibility = View.VISIBLE

            textViewBookmark.visibility = View.VISIBLE
            textViewFriend.visibility = View.VISIBLE
            textViewFindStore.visibility = View.VISIBLE

            imageButtonPlus.visibility = View.GONE
            imageButtonMinus.visibility = View.GONE
        }
        isFloatingMenuButtonClicked = true
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
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}
