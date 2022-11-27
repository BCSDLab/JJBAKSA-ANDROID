package com.jjbaksa.jjbaksa.ui.search.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jjbaksa.jjbaksa.base.BaseViewModel

class SearchViewModel : BaseViewModel() {
    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _searchKeyword = MutableLiveData<String>()
    val searchKeyword: LiveData<String>
        get() = _searchKeyword

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean>
        get() = _isSearching

    private val _locationData = MutableLiveData<Location>()
    val locationData: LiveData<Location>
        get() = _locationData

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateSearchKeyword(searchKeyWord: String) {
        _searchKeyword.value = searchKeyWord
    }

    fun setSearching(isSearching: Boolean) {
        _isSearching.value = isSearching
    }

    fun setNewLocation(location: Location) {
        _locationData.value = location
    }
}
