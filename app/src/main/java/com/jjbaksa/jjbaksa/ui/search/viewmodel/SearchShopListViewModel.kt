package com.jjbaksa.jjbaksa.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.shop.ShopsResult
import com.jjbaksa.domain.usecase.SearchShopsUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchShopListViewModel @Inject constructor(
    private val searchShopsUseCase: SearchShopsUseCase
) : BaseViewModel() {

    private val _shopsResp = MutableLiveData<ShopsResult>()
    val shopsResp: LiveData<ShopsResult>
        get() = _shopsResp

    private val _errorType = MutableLiveData<ErrorType>()
    val errorType: LiveData<ErrorType>
        get() = _errorType

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
                when (it) {
                    is RespResult.Error -> {
                        _errorType.value = it.errorType
                    }
                    is RespResult.Success -> {
                        _shopsResp.value = it.data!!
                    }
                }
            }.onFailure {
                // Handle error here
            }
        }
    }
}
