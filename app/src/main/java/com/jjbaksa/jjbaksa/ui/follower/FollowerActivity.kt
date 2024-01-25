package com.jjbaksa.jjbaksa.ui.follower

import com.jjbaksa.jjbaksa.R
import android.Manifest
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat
import com.jjbaksa.domain.enums.InquiryCursor
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityFollowerBinding
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.home.NaviHomeFragment
import com.jjbaksa.jjbaksa.ui.mainpage.home.viewmodel.HomeViewModel
import com.jjbaksa.jjbaksa.ui.mainpage.mypage.NaviMyPageFragment
import com.jjbaksa.jjbaksa.ui.mainpage.write.NaviWriteFragment
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import com.jjbaksa.jjbaksa.util.checkPermissionsAndRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerActivity : BaseActivity<ActivityFollowerBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_follower

    private val viewModel: FollowerViewModel by viewModels()

    override fun initView() {
        binding.lifecycleOwner = this
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FollowerFragment())
            .commit()
    }

    override fun subscribe() {}

    override fun initEvent() {
        binding.jjAppBar.setOnClickListener { finish() }
        binding.ivSearch.setOnClickListener {
            binding.etSearch.text?.let {
                if (it.isEmpty()) {
                    KeyboardProvider(this).hideKeyboard(binding.etSearch)
                    showSnackBar(getString(R.string.main_page_search_edit_text_hint))
                }
                else {
                    viewModel.getFollower(null, 10)
                }
            }
        }
    }

    companion object {

    }
}
