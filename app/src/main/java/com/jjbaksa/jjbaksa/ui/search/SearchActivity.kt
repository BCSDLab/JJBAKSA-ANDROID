package com.jjbaksa.jjbaksa.ui.search

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.domain.model.search.Shop
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivitySearchBinding
import com.jjbaksa.jjbaksa.listener.OnClickShopListener
import com.jjbaksa.jjbaksa.listener.PaginationScrollListener
import com.jjbaksa.jjbaksa.ui.mainpage.write.NaviWriteViewModel
import com.jjbaksa.jjbaksa.ui.shop.ShopActivity
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    private val keyboardProvider = KeyboardProvider(this)
    private val viewModel: NaviWriteViewModel by viewModels()
    private val trendTextAdapter: TrendTextAdapter by lazy { TrendTextAdapter(this::onClickTrendKeyword) }
    private val fusedLocationUtil: FusedLocationUtil by lazy {
        FusedLocationUtil(
            this,
            this::locationCallback
        )
    }
    private val autoCompleteKeywordAdapter: AutoCompleteKeywordAdapter by lazy {
        AutoCompleteKeywordAdapter(
            this::onClickKeyword
        )
    }
    private val searchShopAdapter: SearchShopAdapter by lazy {
        SearchShopAdapter(
            this,
            object : OnClickShopListener {
                override fun onClick(item: Shop, position: Int) {
                    val intent = Intent(this@SearchActivity, ShopActivity::class.java).apply {
                        putExtra("place_id", item.placeId)
                    }
                    startActivity(intent)
                }
            }
        )
    }
    private val searchHistoryAdapter: SearchHistoryAdapter by lazy {
        SearchHistoryAdapter(
            this::onClickHistory,
            this::onClickDelete
        )
    }
    override val layoutId: Int
        get() = R.layout.activity_search

    private var currentPage = ""

    override fun initView() {
        with(binding) {
            rvTrend.adapter = trendTextAdapter
            rvKeyword.adapter = autoCompleteKeywordAdapter
            rvShop.adapter = searchShopAdapter
            rvHistory.adapter = searchHistoryAdapter

            val linearLayoutManager = LinearLayoutManager(this@SearchActivity)
            rvShop.layoutManager = linearLayoutManager
            rvShop.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager, 20) {
                override fun loading() {
                    if (!currentPage.isEmpty())
                        searchShopAdapter.addLoading()
                }

                override fun loadMoreItems() {
                    if (!currentPage.isEmpty())
                        viewModel.searchPage(currentPage)
                }
            })
        }
        viewModel.getTrendingText()
        viewModel.initSearchHistory()
    }

    override fun subscribe() {
        viewModel.trendTextData.observe(this) {
            trendTextAdapter.submitList(it)
        }
        viewModel.searchHistoryData.observe(this) {
            searchHistoryAdapter.submitList(it.reversed())
        }
        viewModel.autoCompleteData.observe(this) {
            autoCompleteKeywordAdapter.submitList(it)
            autoCompleteKeywordAdapter.notifyDataSetChanged()
        }
        viewModel.shopData.observe(this) {
            if (it.shopQueryResponseList.isEmpty()) {
                if (searchShopAdapter.itemCount == 0) {
                    binding.rvTrend.visibility = View.VISIBLE
                    binding.llEmptyShop.visibility = View.VISIBLE
                    binding.rvShop.visibility = View.GONE
                }
            } else {
                binding.rvTrend.visibility = View.GONE
                binding.llEmptyShop.visibility = View.GONE
                binding.rvShop.visibility = View.VISIBLE
                currentPage = it.pageToken
                searchShopAdapter.removeLoading()
                searchShopAdapter.addAll(it.shopQueryResponseList)
            }
        }
        viewModel.isLoading.observe(this) {
            if (it) {
                showLoading()
            } else {
                dismissLoading()
            }
        }
    }

    override fun initEvent() {
        with(binding) {
            etSearch.setOnFocusChangeListener { view, b ->
                if (b) {
                    tvSearchTitle.visibility = View.GONE
                }
            }
            etSearch.addTextChangedListener {
                clKeywordHistory.visibility = View.VISIBLE
                rvKeyword.visibility = View.VISIBLE
                rvShop.visibility = View.GONE
                viewModel.getAutoCompleteKeyword(it.toString())
            }
            ivSearch.setOnClickListener {
                search(etSearch.text.toString())
            }
            tvClearAll.setOnClickListener {
                viewModel.clearSearchHistory()
            }
            appbarSearch.ivAppbarBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun onClickKeyword(keyword: String) {
        binding.etSearch.setText(keyword)
        autoCompleteKeywordAdapter.submitList(listOf())
        autoCompleteKeywordAdapter.notifyDataSetChanged()
        binding.rvKeyword.visibility = View.GONE
    }
    private fun onClickHistory(keyword: String) {
        binding.etSearch.setText(keyword)
        search(keyword)
    }
    private fun onClickDelete(keyword: String) {
        viewModel.deleteSearchHistory(keyword)
    }
    private fun locationCallback(latitude: Double, longitude: Double) {
        viewModel.setLocation(latitude, longitude)
    }

    private fun search(text: String) {
        binding.run {
            clKeywordHistory.visibility = View.GONE
            tvSearchTitle.visibility = View.GONE
            viewModel.searchKeyword(text)
            searchShopAdapter.clear()
            keyboardProvider.hideKeyboard(etSearch)
            viewModel.saveSearchHistory(text)
        }
    }

    override fun onStart() {
        fusedLocationUtil.startLocationUpdate()
        super.onStart()
    }

    override fun onStop() {
        fusedLocationUtil.stopLocationUpdates()
        super.onStop()
    }

    private fun onClickTrendKeyword(trendText: String) {
        binding.etSearch.setText(trendText)
    }
}
