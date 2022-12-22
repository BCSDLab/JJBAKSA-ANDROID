package com.jjbaksa.jjbaksa.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.resp.shop.ShopsResp
import com.jjbaksa.domain.usecase.SearchShopsUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchShopListViewModel @Inject constructor(
    private val searchShopsUseCase: SearchShopsUseCase
) : BaseViewModel() {

    private val _shopsResp = MutableLiveData<ShopsResp>()
    val shopsResp: LiveData<ShopsResp>
        get() = _shopsResp

    fun searchShops(
        keyword: String,
        x: Double,
        y: Double,
        page: Int = 0,
        size: Int = 10
    ) {
        viewModelScope.launch(ceh) {
            runCatching {
                searchShopsUseCase(keyword, x, y, page, size)
            }.onSuccess {
                _shopsResp.value = it
            }.onFailure {
                // Handle error here
            }
        }
    }
}
