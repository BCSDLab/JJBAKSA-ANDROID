package com.jjbaksa.jjbaksa.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.repository.SearchRepository
import com.jjbaksa.domain.resp.search.ShopData
import com.jjbaksa.jjbaksa.base.BaseViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
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
            searchRepository.getTrendText {
                _trendTextData.value = it
            }
        }
    }
    fun getAutoCompleteKeyword(word: String) {
        viewModelScope.launch(ceh) {
            searchRepository.getSearchKeyword(
                word = word,
                onSuccess = {
                    _autoCompleteData.value = it
                }, onError = {
                _autoCompleteData.value = listOf()
            }
            )
        }
    }
    fun searchKeyword(keyword: String) {
        comparePage = ""
        viewModelScope.launch(ceh) {
            searchRepository.getShops(
                keyword = keyword,
                lat = lat,
                lng = lng,
                onSuccess = {
                    _shopData.value = it
                }, onError = {
            }
            )
        }
    }
    fun searchPage(pageToken: String) {
        if (pageToken != comparePage) {
            Log.e("jdm_tag", "cnt")
            comparePage = pageToken
            viewModelScope.launch(ceh) {
                searchRepository.getShopsPage(
                    pageToken = pageToken,
                    lat = lat,
                    lng = lng,
                    onSuccess = {
                        _shopData.value = it
                    }, onError = {
                }
                )
            }
        }
    }
    fun setLocation(lat: Double, lng: Double) {
        this.lat = lat
        this.lng = lng
    }
}
