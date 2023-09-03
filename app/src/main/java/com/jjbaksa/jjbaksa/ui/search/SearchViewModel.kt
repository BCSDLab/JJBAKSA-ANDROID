package com.jjbaksa.jjbaksa.ui.search

import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.model.search.ShopData
import com.jjbaksa.domain.usecase.GetAutoCompleteKeywordUseCase
import com.jjbaksa.domain.usecase.GetSearchShopUseCase
import com.jjbaksa.domain.usecase.GetTrendingSearchKeyword
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getTrendingSearchKeyword: GetTrendingSearchKeyword,
    private val getAutoCompleteKeywordUseCase: GetAutoCompleteKeywordUseCase,
    private val getSearchShopUseCase: GetSearchShopUseCase
) : BaseViewModel() {
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private val _trendTextData = SingleLiveEvent<List<String>>()
    val trendTextData: SingleLiveEvent<List<String>> get() = _trendTextData

    private val _autoCompleteData = SingleLiveEvent<List<String>>()
    val autoCompleteData: SingleLiveEvent<List<String>> get() = _autoCompleteData

    private val _shopData = SingleLiveEvent<ShopData>()
    val shopData: SingleLiveEvent<ShopData> get() = _shopData

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
            getAutoCompleteKeywordUseCase.invoke(word)
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
}
