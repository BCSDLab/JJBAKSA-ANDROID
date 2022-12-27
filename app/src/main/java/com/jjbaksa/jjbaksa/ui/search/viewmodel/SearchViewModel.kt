package com.jjbaksa.jjbaksa.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jjbaksa.domain.resp.location.GPSData
import com.jjbaksa.domain.usecase.GetCurrentLocationUseCase
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : BaseViewModel() {
    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _searchKeyword = MutableLiveData<String>()
    val searchKeyword: LiveData<String>
        get() = _searchKeyword

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean>
        get() = _isSearching

    private val _locationData = MutableLiveData<GPSData>()
    val locationData: LiveData<GPSData>
        get() = _locationData

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateSearchKeyword(searchKeyWord: String) {
        _searchKeyword.value = searchKeyWord
    }

    fun setSearching(isSearching: Boolean) {
        _isSearching.value = isSearching
    }

    fun getLocation() {
        _isLoading.value = true
        viewModelScope.launch(ceh) {
            runCatching {
                getCurrentLocationUseCase()
            }.onSuccess {
                _isLoading.value = false
                _locationData.value = it
            }
        }
    }
}
