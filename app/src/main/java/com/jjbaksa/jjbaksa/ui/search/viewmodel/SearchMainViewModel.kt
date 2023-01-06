package com.jjbaksa.jjbaksa.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.shop.TrendingResult
import com.jjbaksa.domain.usecase.AddSearchHistoryUseCase
import com.jjbaksa.domain.usecase.GetSearchHistoryUseCase
import com.jjbaksa.domain.usecase.GetTrendingsUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMainViewModel @Inject constructor(
    private val getTrendingsUseCase: GetTrendingsUseCase,
    private val addSearchHistoryUseCase: AddSearchHistoryUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase
) : BaseViewModel() {
    private val _trendings = MutableLiveData<TrendingResult>()
    val trending: LiveData<TrendingResult>
        get() = _trendings

    private val _errorType = MutableLiveData<ErrorType>()
    val errorType: LiveData<ErrorType>
        get() = _errorType

    private val _searchHistory = MutableLiveData<List<String>>()
    val searchHistory: LiveData<List<String>>
        get() = _searchHistory

    fun getTrendingsData() {
        viewModelScope.launch(ceh) {
            runCatching {
                getTrendingsUseCase()
            }.onSuccess {
                when (it) {
                    is RespResult.Error -> {
                        _errorType.value = it.errorType
                    }
                    is RespResult.Success -> {
                        _trendings.value = it.data!!
                    }
                }
            }.onFailure {
            }
        }
    }

    fun addSearchHistory(searchKeyword: String) {
        viewModelScope.launch(ceh) {
            kotlin.runCatching {
                addSearchHistoryUseCase(searchKeyword)
            }.onSuccess { }.onFailure { }
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch(ceh) {
            kotlin.runCatching {
                getSearchHistoryUseCase()
            }.onSuccess {
                _searchHistory.value = it
            }.onFailure {
            }
        }
    }
}
