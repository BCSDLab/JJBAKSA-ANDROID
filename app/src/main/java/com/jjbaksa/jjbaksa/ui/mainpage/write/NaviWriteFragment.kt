package com.jjbaksa.jjbaksa.ui.mainpage.write

import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.domain.model.search.Shop
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentNaviWriteBinding
import com.jjbaksa.jjbaksa.listener.OnClickShopListener
import com.jjbaksa.jjbaksa.listener.PaginationScrollListener
import com.jjbaksa.jjbaksa.ui.mainpage.MainPageActivity
import com.jjbaksa.jjbaksa.ui.pin.PinReviewWriteActivity
import com.jjbaksa.jjbaksa.ui.mainpage.home.NaviHomeFragment
import com.jjbaksa.jjbaksa.ui.search.AutoCompleteKeywordAdapter
import com.jjbaksa.jjbaksa.ui.search.SearchHistoryAdapter
import com.jjbaksa.jjbaksa.ui.search.SearchShopAdapter
import com.jjbaksa.jjbaksa.ui.search.TrendTextAdapter
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaviWriteFragment : BaseFragment<FragmentNaviWriteBinding>() {
    private var backClickTime = 0L

    override val layoutId: Int
        get() = R.layout.fragment_navi_write
    override var onBackPressedCallBack: OnBackPressedCallback? =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.findFragmentByTag(NaviHomeFragment.TAG)?.isVisible == true) {
                    if (System.currentTimeMillis() - backClickTime >= 2000L) {
                        backClickTime = System.currentTimeMillis()
                        showSnackBar(getString(R.string.back_finish))
                    } else {
                        requireActivity().finish()
                    }
                }
                (requireActivity() as MainPageActivity).showHomeFragment()
            }
        }
    private val keyboardProvider: KeyboardProvider by lazy { KeyboardProvider(requireContext()) }
    private val viewModel: NaviWriteViewModel by viewModels()
    private val trendTextAdapter: TrendTextAdapter by lazy { TrendTextAdapter(this::onClickTrendKeyword) }
    private val searchHistoryAdapter: SearchHistoryAdapter by lazy {
        SearchHistoryAdapter(
            this::onClickHistory,
            this::onClickDelete
        )
    }
    private val fusedLocationUtil: FusedLocationUtil by lazy {
        FusedLocationUtil(
            requireContext(),
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
            requireContext(),
            object : OnClickShopListener {
                override fun onClick(item: Shop, position: Int) {
                    val intent = Intent(requireContext(), PinReviewWriteActivity::class.java)
                    intent.putExtra("place_id", item.placeId)
                    intent.putExtra("name", item.name)
                    startActivity(intent)
                }
            }
        )
    }
    private var currentPage = ""
    override fun initView() {
        val spannableString = SpannableString(getString(R.string.write_review_title))
        val foregroundSpan = ForegroundColorSpan(resources.getColor(R.color.color_ff7f23))
        spannableString.setSpan(
            foregroundSpan,
            9,
            12,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        with(binding) {
            tvWriteTitle.text = spannableString
            rvTrend.adapter = trendTextAdapter
            rvKeyword.adapter = autoCompleteKeywordAdapter
            rvShop.adapter = searchShopAdapter
            rvHistory.adapter = searchHistoryAdapter

            val linearLayoutManager = LinearLayoutManager(requireContext())
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

    override fun initEvent() {
        with(binding) {
            etSearch.setOnFocusChangeListener { view, b ->
                if (b) {
                    tvWriteTitle.visibility = View.GONE
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
            appbarSearch.ivAppbarBack.setOnClickListener {
                KeyboardProvider(requireContext()).hideKeyboard(etSearch)
                (requireActivity() as MainPageActivity).showHomeFragment()
            }
            tvClearAll.setOnClickListener {
                viewModel.clearSearchHistory()
            }
        }
    }

    override fun subscribe() {
        viewModel.trendTextData.observe(viewLifecycleOwner) {
            trendTextAdapter.submitList(it)
        }
        viewModel.searchHistoryData.observe(viewLifecycleOwner) {
            searchHistoryAdapter.submitList(it.reversed())
        }
        viewModel.autoCompleteData.observe(viewLifecycleOwner) {
            autoCompleteKeywordAdapter.submitList(it)
            autoCompleteKeywordAdapter.notifyDataSetChanged()
        }
        viewModel.shopData.observe(viewLifecycleOwner) {
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
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                showLoading()
            } else {
                dismissLoading()
            }
        }
    }

    private fun locationCallback(latitude: Double, longitude: Double) {
        viewModel.setLocation(latitude, longitude)
    }

    private fun onClickTrendKeyword(trendText: String) {
        binding.etSearch.setText(trendText)
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
    private fun search(text: String) {
        binding.run {
            clKeywordHistory.visibility = View.GONE
            tvWriteTitle.visibility = View.GONE
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

    companion object {
        fun newInstance() = NaviWriteFragment()
        val TAG = "NaviWriteFragment"
    }
}
