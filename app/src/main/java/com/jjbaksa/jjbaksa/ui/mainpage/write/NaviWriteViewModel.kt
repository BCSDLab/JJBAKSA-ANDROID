package com.jjbaksa.jjbaksa.ui.mainpage.write

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.search.ShopData
import com.jjbaksa.domain.usecase.GetAutoCompleteKeywordUseCase
import com.jjbaksa.domain.usecase.GetSearchHistoryUseCase
import com.jjbaksa.domain.usecase.GetSearchShopUseCase
import com.jjbaksa.domain.usecase.GetTrendingSearchKeyword
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaviWriteViewModel @Inject constructor(
    private val getTrendingSearchKeyword: GetTrendingSearchKeyword,
    private val getAutoCompleteKeywordUseCase: GetAutoCompleteKeywordUseCase,
    private val getSearchShopUseCase: GetSearchShopUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase
) : BaseViewModel() {
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private val _trendTextData = SingleLiveEvent<List<String>>()
    val trendTextData: SingleLiveEvent<List<String>> get() = _trendTextData

    private val _autoCompleteData = SingleLiveEvent<List<String>>()
    val autoCompleteData: SingleLiveEvent<List<String>> get() = _autoCompleteData

    private val _shopData = SingleLiveEvent<ShopData>()
    val shopData: SingleLiveEvent<ShopData> get() = _shopData

    private val _searchHistoryData = SingleLiveEvent<List<String>>()
    val searchHistoryData: SingleLiveEvent<List<String>> get() = _searchHistoryData

    var comparePage: String = ""

    fun getTrendingText() {
        viewModelScope.launch(ceh) {
            getTrendingSearchKeyword.invoke()
                .collect {
                    it.onSuccess { _trendTextData.value = it }
                }
        }
    }

    fun getAutoCompleteKeyword(word: String) {
        viewModelScope.launch(ceh) {
            getAutoCompleteKeywordUseCase(word, lat, lng)
                .collect {
                    it.onSuccess { _autoCompleteData.value = it }
                }
        }
    }

    fun searchKeyword(keyword: String) {
        comparePage = ""
        viewModelScope.launch(ceh) {
            getSearchShopUseCase.firstSearch(keyword, lat, lng)
                .collect {
                    it.onSuccess { _shopData.value = it }
                }
        }
    }

    fun searchPage(pageToken: String) {
        if (pageToken != comparePage) {
            comparePage = pageToken
            viewModelScope.launch(ceh) {
                getSearchShopUseCase.pagingSearch(pageToken, lat, lng)
                    .collect {
                        it.onSuccess { _shopData.value = it }
                    }
            }
        }
    }

    fun setLocation(lat: Double, lng: Double) {
        this.lat = lat
        this.lng = lng
    }

    fun getSearchHistory() {
        viewModelScope.launch(ceh) {
            getSearchHistoryUseCase().collect {
                it.onSuccess { _searchHistoryData.value = it }
            }
        }
    }
    fun saveSearchHistory(keyword: String) {
        viewModelScope.launch(ceh) {
            if(isDuplicatedHistory(keyword)) {
                deleteSearchHistory(keyword)
            }
            getSearchHistoryUseCase.saveSearchHistory(keyword)
            _searchHistoryData.value = _searchHistoryData.value?.plus(keyword)
        }
    }

    fun deleteSearchHistory(keyword: String) {
        _searchHistoryData.value = _searchHistoryData.value?.filter { it != keyword }
        viewModelScope.launch(ceh) {
            getSearchHistoryUseCase.deleteSearchHistory(keyword)
        }
    }

    private fun isDuplicatedHistory(keyword: String): Boolean {
        return _searchHistoryData.value?.contains(keyword) ?: false
    }
}
