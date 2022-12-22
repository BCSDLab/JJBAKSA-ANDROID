package com.jjbaksa.jjbaksa.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search_shop_list, container, false)

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
            binding.constraintLayoutSearchShopListMapChooser.visibility = View.VISIBLE
        }

        binding.buttonSearchShopListNaverMap.setOnClickListener {
        }

        binding.buttonSearchShopListKakaoMap.setOnClickListener {
        }

        binding.buttonSearchShopListCloseMapChooser.setOnClickListener {
            binding.constraintLayoutSearchShopListMapChooser.visibility = View.GONE
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
