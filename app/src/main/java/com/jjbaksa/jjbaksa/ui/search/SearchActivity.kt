package com.jjbaksa.jjbaksa.ui.search

import android.view.MenuItem
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initView() {
        binding.lifecycleOwner = this
        binding.toolbarSearchTitle.setNavigationIcon(R.drawable.ic_toolbar_back)
        setSupportActionBar(binding.toolbarSearchTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun subscribe() {
    }

    override fun initEvent() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
