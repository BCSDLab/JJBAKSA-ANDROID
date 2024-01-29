package com.jjbaksa.jjbaksa.ui.follower

import com.jjbaksa.jjbaksa.R
import androidx.activity.viewModels
import com.jjbaksa.domain.enums.UserCursor
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityFollowerBinding
import com.jjbaksa.jjbaksa.ui.follower.viewmodel.FollowerViewModel
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
                    showSnackBar(getString(R.string.main_page_search_edit_text_hint))
                    viewModel.cursor.value = UserCursor.FOLLOWER
                } else {
                    viewModel.getUserSearch(it.toString(), 20, null )
                    viewModel.cursor.value = UserCursor.ALL
                }

            }
        }
    }

}
