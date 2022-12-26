package com.jjbaksa.jjbaksa.ui.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.domain.resp.shop.ShopsResultContent
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.adapter.SearchShopListAdapter
import com.jjbaksa.jjbaksa.databinding.FragmentSearchShopListBinding
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchShopListViewModel
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchShopListFragment : Fragment() {

    private lateinit var binding: FragmentSearchShopListBinding
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val searchShopListViewModel: SearchShopListViewModel by viewModels()

    private val searchShopListAdapter = SearchShopListAdapter()

    private lateinit var chosenPlace: ShopsResultContent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_search_shop_list,
                container,
                false
            )

        searchViewModel.updateTitle(searchViewModel.searchKeyword.value!!)
        searchViewModel.setSearching(false)

        searchShops()

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

        binding.recyclerViewSearchShopListShopList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = searchShopListAdapter
            addItemDecoration(dividerItemDecoration)
        }

        searchShopListViewModel.shopsResp.observe(viewLifecycleOwner) {
            searchShopListAdapter.submitList(it.content)
        }

        searchShopListAdapter.setOnClickListener {
            chosenPlace = it
            binding.constraintLayoutSearchShopListMapChooser.visibility = View.VISIBLE
        }

        binding.buttonSearchShopListNaverMap.setOnClickListener {
            try {
                val naverMapUrl = "nmap://map?" +
                    "lat=${chosenPlace.x}" +
                    "&lng=${chosenPlace.y}" +
                    "&zoom=20" +
                    "&appname=${context?.packageName}"

                val naverMapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(naverMapUrl))
                startActivity(naverMapIntent)
            } catch (e: ActivityNotFoundException) {
                // If naver map not installed
                val naverMapMarket = "market://details?id=com.nhn.android.nmap"
                val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse(naverMapMarket))
                startActivity(marketIntent)
            }
        }

        binding.buttonSearchShopListKakaoMap.setOnClickListener {
            try {
                val kakaoMapUrl = "kakaomap://look?p=${chosenPlace.x},${chosenPlace.y}"

                val kakaoMapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(kakaoMapUrl))
                startActivity(kakaoMapIntent)
            } catch (e: ActivityNotFoundException) {
                // If kakao map not installed
                val kakaoMapMarket = "market://details?id=net.daum.android.map"
                val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse(kakaoMapMarket))
                startActivity(marketIntent)
            }
        }

        binding.buttonSearchShopListCloseMapChooser.setOnClickListener {
            binding.constraintLayoutSearchShopListMapChooser.visibility = View.GONE
        }

        searchShopListViewModel.errorType.observe(viewLifecycleOwner) {
            Toast.makeText(context, "${it.code}: ${it.errorMessage}", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun searchShops() {
        if (searchViewModel.locationData.value != null) {
            searchShopListViewModel.searchShops(
                searchViewModel.searchKeyword.value!!,
                searchViewModel.locationData.value!!.longitude,
                searchViewModel.locationData.value!!.latitude
            )
        } else {
            searchViewModel.locationData.observe(viewLifecycleOwner) {
                if (it != null) {
                    searchShops()
                }
            }
        }
    }
}
