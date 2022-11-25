package com.jjbaksa.jjbaksa.ui.search

import android.Manifest
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySearchBinding
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchMainViewModel
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchViewModel
import com.jjbaksa.jjbaksa.util.OpenSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_search

    private val searchViewModel: SearchViewModel by viewModels()
    private val searchMainViewModel: SearchMainViewModel by viewModels()

    override fun initView() {
        binding.lifecycleOwner = this
        binding.toolbarSearchTitle.setNavigationIcon(R.drawable.ic_toolbar_back)
        setSupportActionBar(binding.toolbarSearchTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun subscribe() {
        searchViewModel.title.observe(this) {
            supportActionBar?.title = it
        }
    }

    override fun initEvent() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (searchViewModel.isSearching.value == true) {
                    searchViewModel.setSearching(false)
                } else {
                    this.onBackPressed()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
