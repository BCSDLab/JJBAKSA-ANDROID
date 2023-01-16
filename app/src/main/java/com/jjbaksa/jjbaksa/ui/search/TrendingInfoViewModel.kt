package com.jjbaksa.jjbaksa.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.base.BaseState
import com.jjbaksa.domain.usecase.GetTrendingKeywordUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingInfoViewModel @Inject constructor(
    private val getTrendingKeywordUseCase: GetTrendingKeywordUseCase
) : BaseViewModel() {

    private val _trendKeywordState = MutableLiveData<BaseState>(BaseState.Uninitialized)
    val trendKeywordState: LiveData<BaseState> get() = _trendKeywordState

    fun getTrendKeywordList() {
        viewModelScope.launch(ceh) {
            _trendKeywordState.value = BaseState.Loading
            _trendKeywordState.value = getTrendingKeywordUseCase.invoke()
        }
    }
}
