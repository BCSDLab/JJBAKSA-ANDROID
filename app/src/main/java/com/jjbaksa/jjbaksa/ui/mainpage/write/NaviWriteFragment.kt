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
import com.jjbaksa.jjbaksa.ui.search.AutoCompleteKeywordAdapter
import com.jjbaksa.jjbaksa.ui.search.SearchShopAdapter
import com.jjbaksa.jjbaksa.ui.search.TrendTextAdapter
import com.jjbaksa.jjbaksa.util.FusedLocationUtil
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaviWriteFragment : BaseFragment<FragmentNaviWriteBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_navi_write
    override var onBackPressedCallBack: OnBackPressedCallback? = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (requireActivity() as MainPageActivity).showHomeFragment()
        }
    }
    private val keyboardProvider: KeyboardProvider by lazy { KeyboardProvider(requireContext()) }
    private val naviWriteViewModel: NaviWriteViewModel by viewModels()
    private val trendTextAdapter: TrendTextAdapter by lazy { TrendTextAdapter(this::onClickTrendKeyword) }
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

            val linearLayoutManager = LinearLayoutManager(requireContext())
            rvShop.layoutManager = linearLayoutManager
            rvShop.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager, 20) {
                override fun loading() {
                    if (!currentPage.isEmpty())
                        searchShopAdapter.addLoading()
                }

                override fun loadMoreItems() {
                    if (!currentPage.isEmpty())
                        naviWriteViewModel.searchPage(currentPage)
                }
            })
        }
        naviWriteViewModel.getTrendingText()
    }

    override fun initEvent() {
        with(binding) {
            etSearch.setOnFocusChangeListener { view, b ->
                if (b) {
                    tvWriteTitle.visibility = View.GONE
                }
            }
            etSearch.addTextChangedListener {
                rvKeyword.visibility = View.VISIBLE
                rvShop.visibility = View.GONE
                naviWriteViewModel.getAutoCompleteKeyword(it.toString())
            }
            ivSearch.setOnClickListener {
                rvKeyword.visibility = View.GONE
                tvWriteTitle.visibility = View.GONE
                naviWriteViewModel.searchKeyword(etSearch.text.toString())
                searchShopAdapter.clear()
                keyboardProvider.hideKeyboard(etSearch)
            }
            appbarSearch.ivAppbarBack.setOnClickListener {
                (requireActivity() as MainPageActivity).showHomeFragment()
            }
        }
    }

    override fun subscribe() {
        naviWriteViewModel.trendTextData.observe(viewLifecycleOwner) {
            trendTextAdapter.submitList(it)
        }
        naviWriteViewModel.autoCompleteData.observe(viewLifecycleOwner) {
            autoCompleteKeywordAdapter.submitList(it)
            autoCompleteKeywordAdapter.notifyDataSetChanged()
        }
        naviWriteViewModel.shopData.observe(viewLifecycleOwner) {
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
        naviWriteViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                showLoading()
            } else {
                dismissLoading()
            }
        }
    }
    private fun locationCallback(latitude: Double, longitude: Double) {
        naviWriteViewModel.setLocation(latitude, longitude)
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
