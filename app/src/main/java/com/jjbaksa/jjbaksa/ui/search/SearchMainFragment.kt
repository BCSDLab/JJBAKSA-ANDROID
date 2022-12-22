package com.jjbaksa.jjbaksa.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.adapter.SearchHistoryAdapter
import com.jjbaksa.jjbaksa.adapter.SearchTrendingAdapter
import com.jjbaksa.jjbaksa.databinding.FragmentSearchMainBinding
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchMainViewModel
import com.jjbaksa.jjbaksa.ui.search.viewmodel.SearchViewModel
import com.jjbaksa.jjbaksa.util.ItemMarginDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class SearchMainFragment : Fragment() {

    private lateinit var binding: FragmentSearchMainBinding

    private val searchTrendingAdapter = SearchTrendingAdapter()
    private val searchHistoryAdapter = SearchHistoryAdapter()

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val searchMainViewModel: SearchMainViewModel by activityViewModels()

    private var isTyping = false

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (searchViewModel.isSearching.value == true) {
                searchViewModel.setSearching(false)
            } else {
                isEnabled = false
                activity?.onBackPressed()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search_main, container, false)

        searchViewModel.updateTitle(getString(R.string.search_activity_title))
        searchViewModel.setSearching(false)

        val titleArray = resources.getStringArray(R.array.search_titles_array)

        val randomTitle = titleArray[Random.nextInt(titleArray.size)]
        binding.textViewSearchMainTitle.text = randomTitle

        val itemMarginDecoration = ItemMarginDecoration(LinearLayoutManager.HORIZONTAL, 8)

        binding.recyclerViewSearchMainTrending.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = searchTrendingAdapter
            addItemDecoration(itemMarginDecoration)
        }

        binding.recyclerViewSearchMainSearchHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            adapter = searchHistoryAdapter
        }

        searchMainViewModel.trending.observe(viewLifecycleOwner) {
            searchTrendingAdapter.submitList(it.trendings)
        }

        searchMainViewModel.getTrendingsData()

        searchTrendingAdapter.setOnClickListener {
            search(it)
        }

        searchHistoryAdapter.setOnClickListener {
            search(it)
        }

        binding.jjEditTextSearchMainSearchBox.setOnClickListener {
            enableSearching(true)
        }

        binding.jjEditTextSearchMainSearchBox.setOnFocusChangeListener { _, hasFocus ->
            val isSearching = !(!hasFocus && !isTyping)
            if (isSearching) {
                enableSearching(true)
            }
        }

        binding.jjEditTextSearchMainSearchBox.setOnEditorActionListener { view, _, _ ->
            search(view.text.toString())
        }

        binding.jjEditTextSearchMainSearchBox.addTextChangedListener {
            isTyping = !it.isNullOrEmpty()
        }

        binding.imageButtonSearchMainSearchButton.setOnClickListener {
            search(binding.jjEditTextSearchMainSearchBox.editTextText)
        }

        searchViewModel.isSearching.observe(viewLifecycleOwner) {
            if (!it) {
                enableSearching(false)
                binding.jjEditTextSearchMainSearchBox.editTextText = ""
                binding.jjEditTextSearchMainSearchBox.clearFocus()
            }
        }

        searchMainViewModel.searchHistory.observe(viewLifecycleOwner) {
            searchHistoryAdapter.submitList(it)
        }

        return binding.root
    }

    private fun enableSearching(isSearching: Boolean) {
        if (isSearching) {
            with(binding) {
                textViewSearchMainTitle.visibility = View.GONE
                textViewSearchMainTrendingTitle.visibility = View.GONE
                recyclerViewSearchMainTrending.visibility = View.GONE

                recyclerViewSearchMainSearchHistory.visibility = View.VISIBLE
                searchMainViewModel.getSearchHistory()
            }
            searchViewModel.setSearching(true)
        } else {
            with(binding) {
                textViewSearchMainTitle.visibility = View.VISIBLE
                textViewSearchMainTrendingTitle.visibility = View.VISIBLE
                recyclerViewSearchMainTrending.visibility = View.VISIBLE

                recyclerViewSearchMainSearchHistory.visibility = View.GONE
            }
        }
    }

    private fun search(searchKeyword: String) {
        searchViewModel.updateSearchKeyword(searchKeyword)
        searchMainViewModel.addSearchHistory(searchKeyword)
        findNavController().navigate(R.id.action_search_nav_graph_move_to_search_result)
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.remove()
    }
}
